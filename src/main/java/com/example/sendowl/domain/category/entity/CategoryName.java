package com.example.sendowl.domain.category.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public enum CategoryName {

    FREE("FREE", "자유게시판"),
    ANONYMOUS("ANONYMOUS", "익명게시판"),
    DEVELOPER("DEVELOPER", "개발게시판");

    private final String key;
    private final String title;
}
