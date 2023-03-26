package com.example.sendowl.auth.exception.enums;

import com.example.sendowl.common.exception.enums.BaseErrorCodeIF;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum TokenErrorCode implements BaseErrorCodeIF {

    NOT_FOUND(HttpStatus.NOT_FOUND, "토큰이 존재하지 않습니다."),
    INVALID(HttpStatus.NOT_ACCEPTABLE, "유효하지 않은 토큰입니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "토큰 검증에 실패했습니다."),
    EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    NOT_EQUALS(HttpStatus.UNAUTHORIZED, "토큰이 일치하지 않습니다.");
    private final HttpStatus errorStatus;
    private final String errorMessage;

    TokenErrorCode(HttpStatus errorStatus, String errorMessage) {
        this.errorStatus = errorStatus;
        this.errorMessage = errorMessage;
    }

}
