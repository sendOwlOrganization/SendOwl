package com.example.sendowl.common.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BaseResponseDto<T> {

    boolean success;
    T response;

    public BaseResponseDto(T response) {
        this.success = true;
        this.response = response;
    }

}
