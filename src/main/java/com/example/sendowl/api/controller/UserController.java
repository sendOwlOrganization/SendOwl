package com.example.sendowl.api.controller;


import com.example.sendowl.api.service.UserService;
import com.example.sendowl.domain.user.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static com.example.sendowl.auth.jwt.TokenEnum.REFRESH_TOKEN;
import static com.example.sendowl.domain.user.dto.UserDto.*;

@RestController // 내부적으로 오브젝트랩퍼 잭슨을 사용한다. 시리얼라이즈를 해서 반환한다. toString이 걸려있으면 객체들을 계속 조회하는 경우가 발생한다.
@RequiredArgsConstructor // final이 붙은 객체를 DI해준다.
@RequestMapping(path = "/api/users")
public class UserController {

    final private UserService userService;


    @Operation(summary = "회원가입")
    @PostMapping("/join")
    public ResponseEntity<JoinRes> join(final @Valid @RequestBody JoinReq req) {
        return new ResponseEntity(userService.save(req), HttpStatus.OK);
    }

    @Operation(summary = "로그인")
    @PostMapping("/login") // 로그인
    public ResponseEntity<UserRes> login(final @Valid @RequestBody LoginReq req,
                                         HttpServletResponse servletResponse) {
        UserRes userRes = userService.login(req, servletResponse);
        return new ResponseEntity(userRes, HttpStatus.OK);
    }

    @Operation(summary = "무한 로그인")
    @PostMapping("/login/infinite") // 로그인
    public ResponseEntity<UserRes> infiniteLogin(final @Valid @RequestBody LoginReq req,
                                                 HttpServletResponse servletResponse) {
        UserRes userRes = userService.infiniteLogin(req, servletResponse);
        return new ResponseEntity(userRes, HttpStatus.OK);
    }

    @Operation(summary = "토큰기반 Oauth2인증")
    @PostMapping("/oauth2")
    public ResponseEntity<Oauth2Res> getUserByToken(final @Valid @RequestBody Oauth2Req req, HttpServletResponse servletResponse) {
        Oauth2Res oauth2Res = userService.oauthService(req, servletResponse);
        return new ResponseEntity(oauth2Res, HttpStatus.OK);
    }

    @Operation(summary = "AccessToken 재발급", description = "사용자가 refreshToken 을 이용하여 accessToken을 다시 받고 싶을때 사용한다.", security = {@SecurityRequirement(name = "bearerAuth")})
    @GetMapping("/access-token")
    public ResponseEntity<?> getAccessToken(
            @Parameter(hidden = true) @CookieValue(value = REFRESH_TOKEN, required = true) Cookie cookie,
            HttpServletRequest servletRequest,
            HttpServletResponse servletResponse
    ) {
        userService.getAccessToken(cookie.getValue(), servletRequest, servletResponse);
        return new ResponseEntity(null, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @Operation(summary = "자신의 정보를 가져옴", description = "사용자 자신의 정보를 가져온다", security = {@SecurityRequirement(name = "bearerAuth")})
    @GetMapping("/me")
    public ResponseEntity<UserSelfRes> getUserSelf() {
        return new ResponseEntity(userService.getUserSelf(), HttpStatus.OK);
    }

    @Operation(summary = "id로 유저 검색")
    @GetMapping("/{userId}")
    public ResponseEntity<UserRes> getUserById(@PathVariable Long userId) {
        return new ResponseEntity(userService.getUser(userId), HttpStatus.OK);
    }

    @Operation(summary = "사용자 정보 수정", description = "사용자의 토큰을 활용하여 사용자의 세부정보를 수정", security = {@SecurityRequirement(name = "bearerAuth")})
    @PutMapping("/")
    public ResponseEntity<UserPublicRes> changeUserMbti(final @RequestBody UserDto.UpdateUserReq updateUserReq) {
        return new ResponseEntity(userService.updateUser(updateUserReq), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @Operation(summary = "사용자 프로필 초기화", description = "Oauth 인증 후 사용자 프로필(mbti, 닉네임, 나이, 성별) 설정", security = {@SecurityRequirement(name = "bearerAuth")})
    @PostMapping("/set-profile")
    public ResponseEntity<UserRes> setUser(final @Valid @RequestBody ProfileReq req) {
        return new ResponseEntity(userService.setUserProfile(req), HttpStatus.OK);
    }

    @Operation(summary = "사용자 닉네임 중복 확인")
    @GetMapping("/{nickName}/nickname-exists")
    public ResponseEntity<Boolean> checkUserNickName(final @PathVariable String nickName) {
        return new ResponseEntity(userService.duplicationCheckNickName(nickName), HttpStatus.OK);
    }
}