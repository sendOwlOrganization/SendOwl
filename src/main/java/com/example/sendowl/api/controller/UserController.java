package com.example.sendowl.api.controller;


import com.example.sendowl.common.dto.BaseResponseDto;
import com.example.sendowl.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.example.sendowl.domain.user.dto.UserDto.*;

@RestController // 내부적으로 오브젝트랩퍼 잭슨을 사용한다. 시리얼라이즈를 해서 반환한다. toString이 걸려있으면 객체들을 계속 조회하는 경우가 발생한다.
@RequiredArgsConstructor // final이 붙은 객체를 DI해준다.
@RequestMapping(path = "/api/users")
public class UserController {

    final private UserService userService;

    @PostMapping("/join")
    public BaseResponseDto<JoinRes> join(@RequestBody JoinReq req) {
        return new BaseResponseDto<>(userService.save(req));
    }

    @PostMapping("/login") // 로그인
    public BaseResponseDto<LoginRes> login(@RequestBody LoginReq req) {
        return new BaseResponseDto<>(userService.login(req));
    }

    @GetMapping("/{id}")
    public BaseResponseDto<UserRes> getUser(@PathVariable("id") Long id) {
        return new BaseResponseDto<>(userService.getUser(id));
    }
}