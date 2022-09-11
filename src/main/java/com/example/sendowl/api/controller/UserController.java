package com.example.sendowl.api.controller;


import com.example.sendowl.auth.PrincipalDetails;
import com.example.sendowl.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static com.example.sendowl.domain.user.dto.UserDto.*;

@RestController // 내부적으로 오브젝트랩퍼 잭슨을 사용한다. 시리얼라이즈를 해서 반환한다. toString이 걸려있으면 객체들을 계속 조회하는 경우가 발생한다.
@RequiredArgsConstructor // final이 붙은 객체를 DI해준다.
@RequestMapping(path = "/api/users")
public class UserController {

    final private UserService userService;

    @Operation(summary = "사용자 이메일 중복 확인")
    @PostMapping("/email-check")
    public ResponseEntity<EmailCheckRes> emailCheck(final @Valid @RequestBody EmailCheckReq req) {
        return new ResponseEntity(userService.emailCheck(req), HttpStatus.OK);
    }

    @Operation(summary = "사용자 이메일 인증")
    @PostMapping("/verify")
    public ResponseEntity<EmailVerifyRes> emailVerify(final EmailVerifyReq req) {
        return new ResponseEntity(userService.emailVerify(req), HttpStatus.OK);
    }

    @Operation(summary = "회원가입")
    @PostMapping("/join")
    public ResponseEntity<JoinRes> join(final @Valid @RequestBody JoinReq req) {
        return new ResponseEntity(userService.save(req), HttpStatus.OK);
    }

    @Operation(summary = "로그인")
    @PostMapping("/login") // 로그인
    public ResponseEntity<Boolean> login(final @Valid @RequestBody LoginReq req,
                                          HttpServletResponse servletResponse) {
        userService.login(req).forEach(servletResponse::addHeader);
        return new ResponseEntity(true, HttpStatus.OK);
    }
    @Operation(summary = "id로 유저 검색")
    @GetMapping("/{id}")
    public ResponseEntity<UserRes> getUserById(@PathVariable("id") Long id) {
        return new ResponseEntity(userService.getUser(id), HttpStatus.OK);
    }

    @Operation(summary = "토큰기반 Oauth2인증")
    @PostMapping("/oauth2")
    public ResponseEntity<Oauth2Res> getUserByToken(final @Valid @RequestBody Oauth2Req req, HttpServletResponse servletResponse) {
        Oauth2Res oauth2Res = userService.oauthService(req, servletResponse);
        return new ResponseEntity(oauth2Res, HttpStatus.OK);
    }

    @Operation(summary = "Oauth인증 후 사용자 초기화", description = "닉네임, mbti 설정")
    @PostMapping("/set-profile")
    public ResponseEntity<Boolean> setUser( final @Valid @RequestBody ProfileReq req) {
        PrincipalDetails principal = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // mbti는 바로 삽입
        userService.setUserProfile(req, principal.getUser());
        return new ResponseEntity(true, HttpStatus.OK);
    }

    @Operation(summary = "닉네임 중복 확인")
    @GetMapping("/nickname-check")
    public ResponseEntity<Boolean> checkUserNickName(final @Valid @RequestParam("nickname") String nickName) {
        return new ResponseEntity(userService.duplicationCheckNickName(nickName), HttpStatus.OK);
    }

}
