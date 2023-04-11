package com.example.sendowl.api.service;

import com.example.sendowl.api.oauth.GoogleUser;
import com.example.sendowl.api.oauth.Oauth2User;
import com.example.sendowl.api.oauth.exception.Oauth2Exception;
import com.example.sendowl.api.oauth.exception.Oauth2Exception.TransactionIdNotValid;
import com.example.sendowl.api.oauth.exception.enums.Oauth2ErrorCode;
import com.example.sendowl.auth.exception.TokenExpiredException;
import com.example.sendowl.auth.exception.TokenNotEqualsException;
import com.example.sendowl.auth.exception.enums.TokenErrorCode;
import com.example.sendowl.auth.jwt.JwtProvider;
import com.example.sendowl.domain.category.entity.Category;
import com.example.sendowl.domain.category.enums.CategoryErrorCode;
import com.example.sendowl.domain.category.exception.CategoryNotFoundException;
import com.example.sendowl.domain.category.repository.CategoryRepository;
import com.example.sendowl.domain.user.dto.UserMbti;
import com.example.sendowl.domain.user.entity.User;
import com.example.sendowl.domain.user.exception.UserException.UserNotFoundException;
import com.example.sendowl.domain.user.exception.UserException.UserNotValidException;
import com.example.sendowl.domain.user.repository.UserRepository;
import com.example.sendowl.util.DateUtil;
import com.example.sendowl.util.mail.JwtUserParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.example.sendowl.auth.jwt.JwtProvider.REFRESH_TOKEN_VALIDSECOND;
import static com.example.sendowl.auth.jwt.TokenEnum.ACCESS_TOKEN;
import static com.example.sendowl.auth.jwt.TokenEnum.REFRESH_TOKEN;
import static com.example.sendowl.domain.user.dto.UserDto.*;
import static com.example.sendowl.domain.user.exception.enums.UserErrorCode.INVALID_PASSWORD;
import static com.example.sendowl.domain.user.exception.enums.UserErrorCode.NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ExpService expService;

    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final JwtUserParser jwtUserParser;
    private final DateUtil dateUtil;

    @Transactional // write 작업이 있는 메소드에만 달아준다
    public JoinRes save(JoinReq req) {
        String encodedPassword = passwordEncoder.encode(req.getPassword());
        User entity = userRepository.save(req.toEntity(encodedPassword));
        return new JoinRes(entity);
    }

    @Transactional
    public UserRes login(LoginReq req, HttpServletResponse servletResponse) {
        User user = userRepository.findByEmail(req.getEmail()).orElseThrow(
                () -> new UserNotFoundException(NOT_FOUND));
        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new UserNotValidException(INVALID_PASSWORD);
        }

        setAccessToken(servletResponse, jwtProvider.createAccessToken(user));
        setRefreshToken(servletResponse, user, jwtProvider.createRefreshToken());

        return new UserRes(user);
    }

    @Transactional
    public UserRes infiniteLogin(LoginReq req, HttpServletResponse servletResponse) {
        User user = userRepository.findByEmail(req.getEmail()).orElseThrow(
                () -> new UserNotFoundException(NOT_FOUND));
        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new UserNotValidException(INVALID_PASSWORD);
        }

        setAccessToken(servletResponse, jwtProvider.createInfiniteAccessToken(user));
        setRefreshToken(servletResponse, user, jwtProvider.createRefreshToken());

        return new UserRes(user);
    }

    public UserRes getUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(NOT_FOUND));
        return new UserRes(user);
    }

    public UserSelfRes getUserSelf() {
        User user = jwtUserParser.getUser();
        return new UserSelfRes(user);
    }

    @Transactional // write 작업이 있는 메소드에만 달아준다
    public Oauth2Res oauthService(Oauth2Req req, HttpServletResponse servletResponse) {
        // 토큰의 유효성 검증
        Oauth2User oauthUser = getProfileByToken(req.getTransactionId(), req.getToken());
        Boolean alreadyJoined = true;
        Boolean alreadySetted = true;
        User retUser = null;

        // 회원여부 확인
        Optional<User> optionalUser = userRepository.findUserByEmailAndTransactionId(oauthUser.getEmail(), oauthUser.getTransactionId());
        if (optionalUser.isEmpty()) {
            retUser = userRepository.save(
                    User.builder()
                            .email(oauthUser.getEmail())
                            .name(oauthUser.getName())
                            .transactionId(oauthUser.getTransactionId())
                            .build()
            );
            alreadyJoined = false;
        }
        retUser = optionalUser.get();

        // 사용자 초기화 되었는지 확인 - 사용자가 초기화 되지 않은 경우 초기화가 필요함을 알려줌.
        if (retUser.getNickName() == null || retUser.getMbti() == null) {
            alreadySetted = false;
        }

        User user = userRepository.findByEmailAndTransactionId(oauthUser.getEmail(), oauthUser.getTransactionId()).get();

        setAccessToken(servletResponse, jwtProvider.createAccessToken(user));
        setRefreshToken(servletResponse, user, jwtProvider.createRefreshToken());

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

    private void setRefreshToken(HttpServletResponse servletResponse, User user, String refreshToken) {
        Cookie cookie = new Cookie(REFRESH_TOKEN, refreshToken);
        cookie.setMaxAge(REFRESH_TOKEN_VALIDSECOND);
//        cookie.setSecure(true);
//        cookie.setHttpOnly(true);
        // TODO: 리프레쉬 가능 유일 경로 설정 (프론트의 경로에 따라 접근이 달라져서 프론트 개발시 참조)
//        cookie.setPath("/"); //모든 경로에서 접근 가능하도록 설정
        servletResponse.addCookie(cookie);

        user.setRefreshToken(refreshToken);
        user.setRefreshTokenRegDate(dateUtil.getNowLocalDateTime());
    }

    private void setAccessToken(HttpServletResponse servletResponse, String token) {
        servletResponse.addHeader(ACCESS_TOKEN, token);
        servletResponse.addHeader("Access-Control-Expose-Headers", "access-token");
    }

    @Transactional
    public void getAccessToken(String refreshToken, HttpServletRequest request, HttpServletResponse servletResponse) {
        String token = jwtProvider.resolveToken(request, "Bearer");
        Long userId = jwtProvider.getUserId(token);
        User user = userRepository.getById(userId);

        verifyRefreshToken(refreshToken, user);

        setAccessToken(servletResponse, jwtProvider.createAccessToken(user));
        setRefreshToken(servletResponse, user, jwtProvider.createRefreshToken());
    }

    private void verifyRefreshToken(String refreshToken, User user) {
        if (!user.getRefreshToken().equals(refreshToken)) {
            throw new TokenNotEqualsException(TokenErrorCode.NOT_EQUALS);
        }

        LocalDateTime refreshTokenRegDate = user.getRefreshTokenRegDate();
        LocalDateTime validLocalDateTime = refreshTokenRegDate.plusSeconds(REFRESH_TOKEN_VALIDSECOND);
        LocalDateTime nowLocalDateTime = dateUtil.getNowLocalDateTime();

        if (validLocalDateTime.isBefore(nowLocalDateTime)) {
            throw new TokenExpiredException(TokenErrorCode.EXPIRED);
        }
    }
}
