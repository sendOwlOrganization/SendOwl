package com.example.sendowl.domain.exp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExpTypeSize {
    LOGIN(10L, "로그인"),
    BOARD(5L, "게시글"),
    COMMENT(2L, "댓글"),
    LIKE(2L, "좋아요"),
    ACHIEVEMENT(50L, "업적");

    private final Long exp;
    private final String title;
}
