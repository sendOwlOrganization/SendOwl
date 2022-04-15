package com.example.sendowl.common.dto;

import com.example.sendowl.common.exception.BaseException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BaseErrorResponseDto {

    boolean success;
    int errorCode;
    String errorMessage;

    public static BaseErrorResponseDto of(BaseException baseException) {
        return new BaseErrorResponseDto(false, baseException.getErrorCode(), baseException.getErrorMessage());
    }
}
