package com.example.sendowl.api.controller;


import com.example.sendowl.api.service.UserService;
import com.example.sendowl.common.dto.BaseResponseDto;
import com.example.sendowl.domain.user.dto.UserMbti;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // 내부적으로 오브젝트랩퍼 잭슨을 사용한다. 시리얼라이즈를 해서 반환한다. toString이 걸려있으면 객체들을 계속 조회하는 경우가 발생한다.
@RequiredArgsConstructor // final이 붙은 객체를 DI해준다.
@RequestMapping(path = "/api/mbti")
public class MbtiController {

    final private UserService userService;

    @Operation(summary = "유저들의 mbti")
    @PostMapping("/users")
    public BaseResponseDto<List<UserMbti>> getUserByToken() {
        return new BaseResponseDto<>(userService.getUserMbti());
    }

}
