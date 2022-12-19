package com.example.sendowl.domain.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Gender {
    MALE("MALE", "남성"),
    FEMALE("FEMALE", "여성"),
    OTHER("OTHER", "선택안함");

    private final String key;
    private final String title;
}
