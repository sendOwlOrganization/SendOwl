package com.example.sendowl.common.exception.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BaseErrorCode implements BaseErrorCodeIF {

    UNKNOWN_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알수 없는 서버 에러 입니다."),
    NULL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "null 에러입니다.")
    ;

    private final HttpStatus errorStatus;
    private final String errorMessage;

    BaseErrorCode(HttpStatus httpStatus, String errorMessage) {
        this.errorStatus = httpStatus;
        this.errorMessage = errorMessage;
    }
}
