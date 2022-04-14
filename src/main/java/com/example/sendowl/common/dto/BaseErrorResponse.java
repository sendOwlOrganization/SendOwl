package com.example.sendowl.common.dto;

import com.example.sendowl.common.exception.BaseException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BaseErrorResponse {

    int errorCode;
    String errorMessage;

    public static BaseErrorResponse createErrorResponse(BaseException baseException) {
        return new BaseErrorResponse(baseException.getErrorCode(), baseException.getErrorMessage());
    }
}
