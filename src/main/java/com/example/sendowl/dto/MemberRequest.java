package com.example.sendowl.dto;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true) // 초기화를 강제하여 final필드를 0,false,null등으로 바꾸는 생성자
public class MemberRequest {
    @NotNull
    private final String memId;

    @NotNull
    private final String memPw;

    @NotNull
    private final String memName;

    @NotNull
    private final String memEmail;
}
