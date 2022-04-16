package com.example.sendowl.common.dto;

import com.example.sendowl.common.exception.BaseException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BaseErrorResponseDto {

    final boolean success = false;
    int statusCode;
    Map<String, String> errorMessage;

    public static BaseErrorResponseDto of(BaseException baseException) {
        Map<String, String> map = new HashMap<>();
        map.put("message", baseException.getErrorMessage());
        return new BaseErrorResponseDto(baseException.getErrorCode(), map);
    }

    public static BaseErrorResponseDto ofMap(BaseException baseException, Map<String, String> response) {
        return new BaseErrorResponseDto(baseException.getErrorCode(), response);
    }
}
