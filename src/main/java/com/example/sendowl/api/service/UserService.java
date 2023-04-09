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
import com.example.sendowl.domain.user.exception.UserException.UserNotFoundException;
import com.example.sendowl.domain.user.exception.UserException.UserNotValidException;
import com.example.sendowl.domain.user.repository.UserRepository;
import com.example.sendowl.util.mail.JwtUserParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static com.example.sendowl.auth.jwt.JwtEnum.ACCESS_TOKEN;
import static com.example.sendowl.auth.jwt.JwtEnum.REFRESH_TOKEN;
import static com.example.sendowl.domain.user.dto.UserDto.*;
import static com.example.sendowl.domain.user.exception.enums.UserErrorCode.INVALID_PASSWORD;
import static com.example.sendowl.domain.user.exception.enums.UserErrorCode.NOT_FOUND;


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

    @Transactional // write 작업이 있는 메소드에만 달아준다
    public JoinRes save(JoinReq req) {
        String encodedPassword = passwordEncoder.encode(req.getPassword());
        User entity = userRepository.save(req.toEntity(encodedPassword));
        return new JoinRes(entity);
    }

    @Transactional
    public int getUserInfoByKakaoAuthCode(String AuthorizationCode) {
        System.out.println(AuthorizationCode);


        return 1;
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

    public UserSelfRes getUserSelf() {
        User user = jwtUserParser.getUser();
        return new UserSelfRes(user);
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
        else{
        retUser = optionalUser.get();

        // 사용자 초기화 되었는지 확인 - 사용자가 초기화 되지 않은 경우 초기화가 필요함을 알려줌.
        if (retUser.getNickName() == null || retUser.getMbti() == null) {
            alreadySetted = false;
        }
        // 로그인 (토큰 반환)
        makeToken(
                userRepository.findByEmailAndTransactionId(user.getEmail(), user.getTransactionId()).get()
        ).forEach(servletResponse::addHeader);
        servletResponse.addHeader("Access-Control-Expose-Headers", "access-token");
        }

        return new Oauth2Res(alreadyJoined, alreadySetted, retUser);
    }

    public Oauth2User getProfileByToken(String transactionId, String token) {
        String url = "";
        ResponseEntity<Map> response = null;
        Oauth2User oauth2User = null;

        if (transactionId.equals("google")) {
            url = "https://www.googleapis.com/oauth2/v3/userinfo";
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);
            HttpEntity entity = new HttpEntity("", headers);

            try {
                RestTemplate restTemplate = new RestTemplate();
                response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
            } catch (HttpStatusCodeException e) {
                throw new Oauth2Exception.TokenNotValid(Oauth2ErrorCode.UNAUTHORIZED);
            }
        } else if (transactionId.equals("kakao")) {
            final String kakaoAccessToken = this.getKakaoAccessToken(token);
            oauth2User = this.getKakaoUser(kakaoAccessToken);
        } else {
            throw new TransactionIdNotValid(Oauth2ErrorCode.BAD_TRANSACTIONID);
        }

        if (transactionId.equals("google")) {
            oauth2User = new GoogleUser(response.getBody());
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

    private String getKakaoAccessToken(String token) {
        final String URL = "https://kauth.kakao.com/oauth/token";

        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = new MediaType(MediaType.APPLICATION_FORM_URLENCODED, StandardCharsets.UTF_8);
        headers.setContentType(mediaType);

        //Todo: clientId, client_sctet  설정으로 뺄것
        MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();
        parameter.add("grant_type", "authorization_code");
        parameter.add("client_id", "4c4cafe72f1e389d5fb17eeb51da55d5");
        parameter.add("redirect_uri", "http://localhost:8080/api/users/join/kakao");
        parameter.add("code", token);
        parameter.add("client_secret", "jPE1LxCGZMckAFSEni0m3cIXm6d7PpFw");

        ResponseEntity<Map> response = null;
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(parameter, headers);

        try {
            RestTemplate restTemplate = new RestTemplate();
            response = restTemplate.exchange(URL, HttpMethod.POST, entity, Map.class);
        } catch (HttpStatusCodeException e) {
            // 카카오 인증 실패 에러
            throw new Oauth2Exception.TokenNotValid(Oauth2ErrorCode.UNAUTHORIZED);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> map = null;

        return Objects.requireNonNull(response.getBody()).get("access_token").toString();
    };

    private Oauth2User getKakaoUser(String accessToken){
        final String URL = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = new MediaType(MediaType.APPLICATION_FORM_URLENCODED, StandardCharsets.UTF_8);
        headers.setContentType(mediaType);
        headers.add("Authorization", "Bearer " + accessToken);

        MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();

        ResponseEntity<Map> response = null;
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(parameter, headers);

        try {
            RestTemplate restTemplate = new RestTemplate();
            response = restTemplate.exchange(URL, HttpMethod.POST, entity, Map.class);
        }catch (HttpStatusCodeException e) {
            // 카카오 인증 실패 에러
            throw new Oauth2Exception.TokenNotValid(Oauth2ErrorCode.UNAUTHORIZED);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        LinkedHashMap kakaoAccount = objectMapper.convertValue(Objects.requireNonNull(response.getBody()).get("kakao_account"), LinkedHashMap.class);


        return Oauth2User.builder()
                .email(kakaoAccount.get("email").toString()).transactionId("kakao").build();
    };

    private boolean isAlreadyMember(Oauth2User user){
       return userRepository.findByEmail(user.getEmail()).isPresent();
    }
}
