package com.example.sendowl.controller;


import com.example.sendowl.dto.MemberRequest;
import com.example.sendowl.dto.MemberResponse;
import com.example.sendowl.entity.Member;
import com.example.sendowl.service.MemberService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController // 내부적으로 오브젝트랩퍼 잭슨을 사용한다. 시리얼라이즈를 해서 반환한다. toString이 걸려있으면 객체들을 계속 조회하는 경우가 발생한다.
@RequiredArgsConstructor // final이 붙은 객체를 DI해준다.
@RequestMapping(path = "/api/mem")
public class MemberController {

    final private MemberService memberService;

    @Operation(summary = "join api", description = "join api")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK !!"),
            @ApiResponse(code = 201, message = "CREATED !!"),
            @ApiResponse(code = 400, message = "BAD REQUEST !!"),
            @ApiResponse(code = 404, message = "NOT FOUND !!"),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR !!")
    })
    @PostMapping(path = "/join") // join
    public ResponseEntity<MemberResponse> signUp(
            @RequestBody @Valid final MemberRequest memberRequest
            ){

        Member saveduser = memberService.addMember(memberRequest.getMemId(), memberRequest.getMemPw(), memberRequest.getMemName(), memberRequest.getMemEmail());
        final MemberResponse memberResponse = MemberResponse.builder()
                .memId(saveduser.getMemId())
                .memName(saveduser.getMemName())
                .memEmail(saveduser.getMemEmail()).build();
        return new ResponseEntity<MemberResponse>(memberResponse, HttpStatus.CREATED); // 엔티티 그대로 반환하면 안된다. DTO로 변호나하여 쏴주면 ㄱㅊ하다
    }

    @Operation(summary = "login api", description = "login api")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK !!"),
            @ApiResponse(code = 201, message = "CREATED !!"),
            @ApiResponse(code = 400, message = "BAD REQUEST !!"),
            @ApiResponse(code = 404, message = "NOT FOUND !!"),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR !!")
    })
    @PostMapping(path="/login") // 로그인
    public String login(@RequestBody MemberRequest memberRequest) {
        return  memberService.loginMember(memberRequest.getMemEmail(), memberRequest.getMemPw());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity getUser(
            @PathVariable Long id
    ){

        Member saveduser = memberService.getMember(id);
        final MemberResponse memberResponse = MemberResponse.builder()
                .memId(saveduser.getMemId())
                .memName(saveduser.getMemName())
                .memEmail(saveduser.getMemEmail()).build();

        return new ResponseEntity(memberResponse,HttpStatus.OK);
    }
}
