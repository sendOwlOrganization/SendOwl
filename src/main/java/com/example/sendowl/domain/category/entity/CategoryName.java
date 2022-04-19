package com.example.sendowl.domain.category.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CategoryName {

    FREE("FREE", "자유게시판"),
    ANONYMOUS("ANONYMOUS", "익명게시판");

    private final String key;
    private final String title;

}
