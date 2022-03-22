package com.example.sendowl.dto;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class CommentRequest {
    @NotNull
    private final Long boardId;

    @NotNull
    private final Long memberId;
    
    @NotNull
    private final Long parentId;

    @NotNull
    private final String content;

    @NotNull
    private final String regIp;
}
