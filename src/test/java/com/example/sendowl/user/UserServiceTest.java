package com.example.sendowl.user;

import com.example.sendowl.api.service.UserService;
import com.example.sendowl.domain.user.dto.UserDto;
import com.example.sendowl.domain.user.entity.Gender;
import com.example.sendowl.domain.user.entity.User;
import com.example.sendowl.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    private final String mbti = "estj";
    private final String nickName = "testNickName";
    private final Integer age = 20;
    private final Gender gender = Gender.OTHER;
    @InjectMocks // userService는 주입당할 객체이므로 InjectMocks로 선언
    private UserService userService;
    @Mock // userService에서 해당 객체를 사용하기 때문에 주입하기 위해 Mock로 선언
    private UserRepository userRepository;

    @Test
    void setUserTest() {
        UserDto.ProfileReq req = new UserDto.ProfileReq(mbti, nickName, age, gender);
        User user = new User();

        // userRepository의 함수가 호출될때 어떤것을 반환할지 미리 선언해준다.
        given(userRepository.findByEmailAndTransactionId(any(), any()))
                .willReturn(Optional.of(user));

        // 실제로 함수를 실행시킨다.
        UserDto.UserRes userRes = userService.setUserProfile(
                req);

        org.junit.jupiter.api.Assertions.assertEquals(userRes.getMbti(), mbti);
    }
}

