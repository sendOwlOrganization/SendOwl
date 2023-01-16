package com.example.sendowl.api.service;

import com.example.sendowl.api.oauth.GoogleUser;
import com.example.sendowl.api.oauth.Oauth2User;
import com.example.sendowl.api.oauth.exception.Oauth2Exception;
import com.example.sendowl.api.oauth.exception.Oauth2Exception.TransactionIdNotValid;
import com.example.sendowl.api.oauth.exception.enums.Oauth2ErrorCode;
import com.example.sendowl.auth.jwt.JwtProvider;
import com.example.sendowl.domain.category.entity.Category;
import com.example.sendowl.domain.category.enums.CategoryErrorCode;
import com.example.sendowl.domain.category.exception.CategoryNotFoundException;
import com.example.sendowl.domain.category.repository.CategoryRepository;
import com.example.sendowl.domain.user.dto.UserMbti;
import com.example.sendowl.domain.user.entity.User;
import com.example.sendowl.domain.user.exception.UserException.UserAlreadyExistException;
import com.example.sendowl.domain.user.exception.UserException.UserNotFoundException;
import com.example.sendowl.domain.user.exception.UserException.UserNotValidException;
import com.example.sendowl.domain.user.exception.UserException.UserVerifyTokenExpiredException;
import com.example.sendowl.domain.user.repository.UserRepository;
import com.example.sendowl.kafka.producer.KafkaProducer;
import com.example.sendowl.redis.service.RedisEmailTokenService;
import com.example.sendowl.util.mail.JwtUserParser;
import com.example.sendowl.util.mail.TokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.example.sendowl.auth.jwt.JwtEnum.ACCESS_TOKEN;
import static com.example.sendowl.auth.jwt.JwtEnum.REFRESH_TOKEN;
import static com.example.sendowl.domain.user.dto.UserDto.*;
import static com.example.sendowl.domain.user.exception.enums.UserErrorCode.*;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final KafkaProducer kafkaProducer;
    private final RedisEmailTokenService redisEmailTokenService;
    private final JwtUserParser jwtUserParser;

    @Transactional // write 작업이 있는 메소드에만 달아준다
    public JoinRes save(JoinReq req) {
        String encodedPassword = passwordEncoder.encode(req.getPassword());
        User entity = userRepository.save(req.toEntity(encodedPassword));
        return new JoinRes(entity);
    }

    public HashMap<String, String> makeToken(User user) {
        String accessToken = jwtProvider.createAccessToken(user);
        String refreshToken = jwtProvider.createRefreshToken(user);
        return new HashMap<>(Map.of(
                ACCESS_TOKEN, accessToken,
                REFRESH_TOKEN, refreshToken));
    }

    public HashMap<String, String> makeInfiniteToken(User user) {
        String accessToken = jwtProvider.createInfiniteAccessToken(user);
        String refreshToken = jwtProvider.createRefreshToken(user);
        return new HashMap<>(Map.of(
                ACCESS_TOKEN, accessToken,
                REFRESH_TOKEN, refreshToken));
    }

    public Map<String, String> login(LoginReq req) {
        User user = userRepository.findByEmail(req.getEmail()).orElseThrow(
                () -> new UserNotFoundException(NOT_FOUND));
        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new UserNotValidException(INVALID_PASSWORD);
        }
        return makeToken(user);
    }

    public Map<String, String> infiniteLogin(LoginReq req) {
        User user = userRepository.findByEmail(req.getEmail()).orElseThrow(
                () -> new UserNotFoundException(NOT_FOUND));
        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new UserNotValidException(INVALID_PASSWORD);
        }
        return makeInfiniteToken(user);
    }

    public UserRes getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(NOT_FOUND));
        return new UserRes(user);
    }

    public EmailCheckRes emailCheck(EmailCheckReq req) {
        if (userRepository.existsUserByEmail(req.getEmail())) {
            throw new UserAlreadyExistException(EXISTING_EMAIL);
        }

        String token = new TokenGenerator().generateSixRandomNumber();
        new Thread(() -> {
            kafkaProducer.sendEmailVerification(req.getEmail(), token); // 인증 코드 전송
            redisEmailTokenService.save(req.getEmail(), token);
        }).start();


        return new EmailCheckRes().success();
    }

    public EmailVerifyRes emailVerify(EmailVerifyReq req) {
        String token = redisEmailTokenService.getToken(req.getEmail())
                .orElseThrow(() -> new UserVerifyTokenExpiredException(EXPIRED_VERIFICATION_TOKEN));

        return new EmailVerifyRes(token.equals(req.getToken()));
    }

    @Transactional // write 작업이 있는 메소드에만 달아준다
    public Oauth2Res oauthService(Oauth2Req req, HttpServletResponse servletResponse) {
        // 토큰의 유효성 검증
        Oauth2User user = getProfileByToken(req.getTransactionId(), req.getToken());
        Boolean alreadyJoined = true;
        Boolean alreadySetted = true;
        User retUser = null;

        // 회원여부 확인
        Optional<User> optionalUser = userRepository.findUserByEmailAndTransactionId(user.getEmail(), user.getTransactionId());
        if (optionalUser.isEmpty()) {
            retUser = userRepository.save(
                    User.builder()
                            .email(user.getEmail())
                            .name(user.getName())
                            .transactionId(user.getTransactionId())
                            .build()
            );
            alreadyJoined = false;
        }
        retUser = optionalUser.get();

        // 사용자 초기화 되었는지 확인 - 사용자가 초기화 되지 않은 경우 초기화가 필요함을 알려줌.
        if (retUser.getNickName() == null || retUser.getMbti() == null) {
            alreadySetted = false;
        }
        // 로그인 (토큰 반환)
        makeToken(
                userRepository.findByEmailAndTransactionId(user.getEmail(), user.getTransactionId()).get()
        ).forEach(servletResponse::addHeader);

        return new Oauth2Res(alreadyJoined, alreadySetted, retUser);
    }

    public Oauth2User getProfileByToken(String transactionId, String token) {
        String url = "";
        if (transactionId.equals("google")) {
            url = "https://www.googleapis.com/oauth2/v3/userinfo";
        } else if (transactionId.equals("kakao")) {
            url = "";
        } else {
            throw new TransactionIdNotValid(Oauth2ErrorCode.BAD_TRANSACTIONID);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity entity = new HttpEntity("", headers);

        ResponseEntity<Map> response = null;
        try {
            RestTemplate restTemplate = new RestTemplate();
            response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
        } catch (HttpStatusCodeException e) {
            throw new Oauth2Exception.TokenNotValid(Oauth2ErrorCode.UNAUTHORIZED);
        }

        Oauth2User oauth2User = null;
        if (transactionId.equals("google")) {
            oauth2User = new GoogleUser(response.getBody());
        } else if (transactionId.equals("kakao")) {

        } else {

        }
        return oauth2User;
    }

    public List<UserMbti> getUserMbti() {
        return userRepository.findAllWithJPQL();
    }

    public List<UserMbti> getUserMbtiFromCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(CategoryErrorCode.NOT_FOUND));
        return userRepository.findUserMbtiFromCategory(category);
    }

    public boolean duplicationCheckNickName(String nickName) {
        return userRepository.existsUserByNickName(nickName);
    }

    @Transactional
    public UserRes setUserProfile(ProfileReq req) {
        User user = jwtUserParser.getUser();
        User savedUser = userRepository.findByEmailAndTransactionId(user.getEmail(), user.getTransactionId()).get();
        savedUser.setMbti(req.getMbti());
        savedUser.setNickName(req.getNickName());
        savedUser.setAge(req.getAge());
        savedUser.setGender(req.getGender());
        return new UserRes(savedUser);
    }
}
