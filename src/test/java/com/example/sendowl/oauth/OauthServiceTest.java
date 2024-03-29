package com.example.sendowl.oauth;

import com.example.sendowl.domain.user.dto.Oauth2User;
import com.example.sendowl.api.service.UserService;
import com.example.sendowl.auth.jwt.JwtProvider;
import com.example.sendowl.domain.user.dto.UserDto;
import com.example.sendowl.domain.user.entity.User;
import com.example.sendowl.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class OauthServiceTest {
    @Mock
    JwtProvider jwtProvider;
    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;


    @Test
    void getTokenCallOauthService() {
        // given
        User existUser = User.builder().email("sendowl@naver.com").build();

        String transactionId = "google";
        String token = "test";

        // mocking
        given(userRepository.existsUserByEmailAndTransactionId(any(), any())).willReturn(true);
        given(userRepository.findByEmail(any())).willReturn(Optional.of(existUser));

        given(jwtProvider.createAccessToken(any())).willReturn("accessToken");

        // when
        Map<String, String> tokens = (Map<String, String>) userService.oauthService(UserDto.Oauth2Req.builder().transactionId(transactionId).token(token).build(), any());
        // then
    }

}
