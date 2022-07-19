package com.example.sendowl.domain.user.dto;

import lombok.Getter;

@Getter
public class UserMbti {
    private String mbti;
    private Long count;

    public UserMbti(String mbti, Long count) {
        this.mbti = mbti;
        this.count = count;
    }
}
