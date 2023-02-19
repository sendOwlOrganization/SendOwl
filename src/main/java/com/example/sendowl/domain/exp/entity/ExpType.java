package com.example.sendowl.domain.exp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExpType {
    LOGIN("LOGIN", "로그인"),
    BOARD("BOARD", "게시글"),
    COMMENT("COMMENT", "댓글"),
    ACHIEVEMENT("ACHIEVEMENT", "업적");

    private final String key;
    private final String title;
}
