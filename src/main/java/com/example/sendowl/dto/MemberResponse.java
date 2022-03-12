package com.example.sendowl.dto;

import com.sun.istack.NotNull;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true) // 초기화를 강제하여 final필드를 0,false,null등으로 바꾸는 생성자
public class MemberResponse {
    @NotNull
    private final String memId;

    @NotNull
    private final String memName;

    @NotNull
    private final String memEmail;

}
