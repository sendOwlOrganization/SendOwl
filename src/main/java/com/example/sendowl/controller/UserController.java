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

@RestController
@RequiredArgsConstructor // final이 붙은 객체를 DI해준다.
public class UserController {

    final private MemberService memberService;

    @Operation(summary = "SignUp API", description = "SignUp API")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK !!"),
            @ApiResponse(code = 201, message = "CREATED !!"),
            @ApiResponse(code = 400, message = "BAD REQUEST !!"),
            @ApiResponse(code = 404, message = "NOT FOUND !!"),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR !!")
    })
    @PostMapping(path = "/mem")
    public ResponseEntity<MemberResponse> signUp(
            @RequestBody @Valid final MemberRequest userRequest
            ){

        Member saveduser = memberService.addMember(userRequest.getMemId(), userRequest.getMemPw(), userRequest.getMemName(), userRequest.getMemEmail());
        final MemberResponse memberResponse = MemberResponse.builder()
                .memId(saveduser.getMemId())
                .memName(saveduser.getMemName())
                .memEmail(saveduser.getMemEmail()).build();
        return new ResponseEntity<MemberResponse>(memberResponse, HttpStatus.CREATED); // 엔티티 그대로 반환하면 안된다.
    }
    @GetMapping(path = "/mem/{id}")
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
