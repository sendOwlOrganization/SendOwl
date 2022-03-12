package com.example.sendowl.controller;


import com.example.sendowl.dto.MemberRequest;
import com.example.sendowl.dto.MemberResponse;
import com.example.sendowl.entity.Member;
import com.example.sendowl.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor // final이 붙은 객체를 DI해준다.
public class UserController {

    final private MemberService memberService;

    @PostMapping(path = "/mem")
    public ResponseEntity signUp(
            @RequestBody @Valid final MemberRequest userRequest
            ){

        Member saveduser = memberService.addMember(userRequest.getMemId(), userRequest.getMemPw(), userRequest.getMemName(), userRequest.getMemEmail());
        final MemberResponse memberResponse = MemberResponse.builder()
                .memId(saveduser.getMemId())
                .memName(saveduser.getMemName())
                .memEmail(saveduser.getMemEmail()).build();

        return new ResponseEntity(memberResponse, HttpStatus.OK);
    }
    @GetMapping(path = "/mem/{id}")
    public ResponseEntity getUser(
            @PathVariable Long id
    ){

        Member saveduser = memberService.getMember(id);
        return new ResponseEntity(saveduser,HttpStatus.OK);
    }
}
