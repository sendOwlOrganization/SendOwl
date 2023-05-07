package com.example.sendowl.api.service;

import com.example.sendowl.api.oauth.exception.Oauth2Exception;
import com.example.sendowl.auth.jwt.JwtProvider;
import com.example.sendowl.auth.jwt.TokenEnum;
import com.example.sendowl.domain.category.entity.Category;
import com.example.sendowl.domain.category.exception.CategoryNotFoundException;
import com.example.sendowl.domain.category.repository.CategoryRepository;
import com.example.sendowl.domain.user.dto.UserDto;
import com.example.sendowl.domain.user.dto.UserMbti;
import com.example.sendowl.domain.user.entity.Gender;
import com.example.sendowl.domain.user.entity.User;
import com.example.sendowl.domain.user.exception.UserException;
import com.example.sendowl.domain.user.repository.UserRepository;
import com.example.sendowl.util.DateUtil;
import com.example.sendowl.util.mail.JwtUserParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private final Long CATEGORY_ID = 1L;
    private final Long WRONG_CATEGORY_ID = -1L;
    private final String USER_NICKNAME = "userNickname";
    @InjectMocks
    UserService userService;
    @Mock
    UserRepository userRepository;
    @Mock
    CategoryRepository categoryRepository;
    @Mock
    JwtUserParser jwtUserParser;
    @Mock
    JwtProvider jwtProvider;
    @Mock
    DateUtil dateUtil;
    @Mock
    RestTemplate restTemplate;

    @Mock
    PasswordEncoder passwordEncoder;

    private User user;
    private Category category;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .name("user1")
                .nickName(USER_NICKNAME)
                .email("emailUser1")
                .password("passwordUser1")
                .mbti("estj")
                .age(20)
                .gender(Gender.MALE)
                .build();
        category = Category.builder()
                .id(CATEGORY_ID)
                .name("자유게시판").build();
    }

    @Test
    void when_save_then_JoinRes(){
        //given
        when(passwordEncoder.encode(any())).thenReturn("Encoded Password");
        when(userRepository.save(any())).thenReturn(user);
        //when
        UserDto.JoinRes joinRes = userService.save(new UserDto.JoinReq());
        //then
        assertEquals(joinRes.getId(), user.getId());
    }

    @Test
    void when_notMmember_login_then_UserNotFountException(){
        //given
        HttpServletResponse httpServletResponse = new MockHttpServletResponse();
        UserDto.LoginReq loginReq = new UserDto.LoginReq("", "");
        //when
        //then
        Assertions.assertThrows(UserException.UserNotFoundException.class, () -> {
            userService.login(loginReq,httpServletResponse);
        });
    }

    @Test
    void when_passwordNotMatch_login_then_UserNotValidException(){
        //given
        HttpServletResponse httpServletResponse = new MockHttpServletResponse();
        UserDto.LoginReq loginReq = new UserDto.LoginReq("emailUser1", "");
        when(userRepository.findByEmail(any())).thenReturn(Optional.ofNullable(user));
        //when
        //then
        Assertions.assertThrows(UserException.UserNotValidException.class, () -> {
            userService.login(loginReq,httpServletResponse);
        });
    }

    @Test
    void when_login_then_setCookies(){
        //given
        HttpServletResponse httpServletResponse = new MockHttpServletResponse();
        when(passwordEncoder.matches(any(),any())).thenReturn(true);
        when(userRepository.findByEmail(any())).thenReturn(Optional.ofNullable(user));
        when(jwtProvider.createAccessToken(any())).thenReturn("access_token");
        when(jwtProvider.createRefreshToken()).thenReturn("refresh_token");
        when(dateUtil.getNowLocalDateTime()).thenReturn(LocalDateTime.now());
        //when
        UserDto.LoginReq loginReq = new UserDto.LoginReq("emailUser1", "passwordUser1");
        userService.login(loginReq,httpServletResponse);
        //then
        Assertions.assertTrue(httpServletResponse.containsHeader(TokenEnum.ACCESS_TOKEN));
        Assertions.assertTrue(Objects.requireNonNull(httpServletResponse.getHeader("Set-Cookie")).startsWith(TokenEnum.REFRESH_TOKEN));
    }

    void when_login_then_userRes(){
        //given
        //when
        //then
    }
    @Test
    void when_getUserSelf_then_userSelfRes() {
        // given
        when(jwtUserParser.getUser()).thenReturn(user);
        // when
        UserDto.UserSelfRes userSelf = userService.getUserSelf();
        // then
        assertEquals(userSelf.getId(), user.getId());
    }

    @Test
    void when_oauthServiceWithUnkownOauth_then_transactionIdNotValidException() {
        // given
        UserDto.Oauth2Req req = UserDto.Oauth2Req.builder()
                .transactionId("unknown").token("token")
                .build();
        HttpServletResponse res = mock(HttpServletResponse.class);
        // when
        // then
        Assertions.assertThrows(Oauth2Exception.TransactionIdNotValid.class, () -> {
            userService.oauthService(req, res);
        });
    }

    @Test
    void when_oauthServiceWithValidationApiFailed_then_tokenNotValidException() {
        // given
        UserDto.Oauth2Req req = UserDto.Oauth2Req.builder()
                .transactionId("google").token("token")
                .build();
        HttpServletResponse res = mock(HttpServletResponse.class);
        HttpStatusCodeException error = mock(HttpStatusCodeException.class);
        when(restTemplate.exchange(anyString(), any(), any(), (Class<Object>) any())).thenThrow(error);
        // when
        // then
        Assertions.assertThrows(Oauth2Exception.TokenNotValid.class, () -> {
            userService.oauthService(req, res);
        });
    }

    @Test
    @Disabled
    void when_oauthService_then_() {
        // given
        UserDto.Oauth2Req req = UserDto.Oauth2Req.builder()
                .transactionId("google").token("token")
                .build();
        HttpServletResponse res = mock(HttpServletResponse.class);
        Map<String, String> body = new HashMap<>();
        ResponseEntity<Object> response = new ResponseEntity<>(body, HttpStatus.OK);

        when(restTemplate.exchange(anyString(), any(), any(), (Class<Object>) any())).thenReturn(response);
        // when
        // then
        Assertions.assertThrows(Oauth2Exception.TokenNotValid.class, () -> {
            userService.oauthService(req, res);
        });
    }

    @Test
    void when_getUserMbti_then_() {
        // given
        List<UserMbti> res = new ArrayList<>();
        res.add(new UserMbti("enfp", 3L));
        res.add(new UserMbti("istp", 2L));

        when(userRepository.findAllUserMbtiWithCount()).thenReturn(res);
        // when
        List<UserMbti> userMbti = userService.getUserMbti();
        // then
        assertEquals(userMbti.size(), 2);
    }

    @Test
    void when_getUserMbtiFromCategoryIdWithWrongCategoryId_then_categoryNotFoundException() {
        // given
        when(categoryRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        // when
        // then
        Assertions.assertThrows(CategoryNotFoundException.class, () -> {
            userService.getUserMbtiFromCategoryId(WRONG_CATEGORY_ID);
        });
    }

    @Test
    void when_getUserMbtiFromCategoryId_then_listUserMbti() {
        // given
        List<UserMbti> res = new ArrayList<>();
        res.add(new UserMbti("enfp", 3L));
        res.add(new UserMbti("istp", 2L));

        when(categoryRepository.findById(any())).thenReturn(Optional.ofNullable(category));
        when(userRepository.findUserMbtiFromCategory(any())).thenReturn(res);
        // when
        List<UserMbti> userMbtiList = userService.getUserMbtiFromCategoryId(CATEGORY_ID);
        // then
        assertEquals(userMbtiList.size(), 2);
    }

    @Test
    void when_duplicationCheckNickName_then_boolean() {
        // given
        when(userRepository.existsUserByNickName(any())).thenReturn(true);
        // when
        boolean res = userService.duplicationCheckNickName(USER_NICKNAME);
        // then
        assertTrue(res);
    }

    @Test
    void when_setUserProfile_then_userRes() {
        // given
        when(jwtUserParser.getUser()).thenReturn(user);
        when(userRepository.findByEmailAndTransactionId(any(), any())).thenReturn(Optional.ofNullable(user));
        // when
        UserDto.ProfileReq req = new UserDto.ProfileReq(
                user.getMbti(),
                user.getNickName(),
                user.getAge(),
                user.getGender()
        );
        UserDto.UserRes userRes = userService.setUserProfile(req);
        // then
        assertEquals(userRes.getMbti(), user.getMbti());
    }

    @Test
    void when_getAccessToken_then_() {
        // given
        when(jwtUserParser.getUser()).thenReturn(user);
        when(userRepository.findByEmailAndTransactionId(any(), any())).thenReturn(Optional.ofNullable(user));
        // when
        UserDto.ProfileReq req = new UserDto.ProfileReq(
                user.getMbti(),
                user.getNickName(),
                user.getAge(),
                user.getGender()
        );
        UserDto.UserRes userRes = userService.setUserProfile(req);
        // then
        assertEquals(userRes.getMbti(), user.getMbti());
    }

}