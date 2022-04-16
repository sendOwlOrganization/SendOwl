package com.example.sendowl.common.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BaseResponseDto<T> {

    boolean success = true;
    int statusCode;
    T response;

    public BaseResponseDto(T response) {
        this.statusCode = HttpStatus.OK.value();
        this.response = response;
    }

    public BaseResponseDto(T response, HttpStatus statusCode) {
        this.statusCode = statusCode.value();
        this.response = response;
    }

}
