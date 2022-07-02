package com.example.sendowl.api.controller;


import com.example.sendowl.common.dto.BaseResponseDto;
import com.example.sendowl.api.service.UserService;
import com.example.sendowl.domain.user.dto.UserMbti;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.util.List;
import java.util.Map;

import static com.example.sendowl.domain.user.dto.UserDto.*;

@RestController // 내부적으로 오브젝트랩퍼 잭슨을 사용한다. 시리얼라이즈를 해서 반환한다. toString이 걸려있으면 객체들을 계속 조회하는 경우가 발생한다.
@RequiredArgsConstructor // final이 붙은 객체를 DI해준다.
@RequestMapping(path = "/api/users")
public class UserController {

    final private UserService userService;

    @Operation(summary = "사용자 이메일 중복 확인")
    @PostMapping("/email-check")
    public BaseResponseDto<EmailCheckRes> emailCheck(final @Valid @RequestBody EmailCheckReq req) {
        return new BaseResponseDto<>(userService.emailCheck(req));
    }

    @Operation(summary = "사용자 이메일 인증")
    @PostMapping("/verify")
    public BaseResponseDto<EmailVerifyRes> emailVerify(final EmailVerifyReq req) {
        return new BaseResponseDto<>(userService.emailVerify(req));
    }

    @Operation(summary = "회원가입")
    @PostMapping("/join")
    public BaseResponseDto<JoinRes> join(final @Valid @RequestBody JoinReq req) {
        return new BaseResponseDto<>(userService.save(req));
    }

    @Operation(summary = "로그인")
    @PostMapping("/login") // 로그인
    public BaseResponseDto<Boolean> login(final @Valid @RequestBody LoginReq req,
                                          HttpServletResponse servletResponse) {
        userService.login(req).forEach(servletResponse::addHeader);
        return new BaseResponseDto<>(true);
    }
    @Operation(summary = "id로 유저 검색")
    @GetMapping("/{id}")
    public BaseResponseDto<UserRes> getUserById(@PathVariable("id") Long id) {
        return new BaseResponseDto<>(userService.getUser(id));
    }

    @Operation(summary = "토큰기반 Oauth2인증")
    @PostMapping("/oauth2")
    public BaseResponseDto<Boolean> getUserByToken(final @Valid @RequestBody Oauth2Req req, HttpServletResponse servletResponse) {
        userService.oauthService(req).forEach(servletResponse::addHeader);
        return new BaseResponseDto<>(true);
    }

    @Operation(summary = "유저의 mbti")
    @PostMapping("/mbti")
    public BaseResponseDto<List<UserMbti>> getUserByToken() {
        return new BaseResponseDto<>(userService.getUserMbti());
    }

}
