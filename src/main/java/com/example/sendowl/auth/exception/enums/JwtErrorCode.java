package com.example.sendowl.auth.exception.enums;

import com.example.sendowl.common.exception.enums.BaseErrorCodeIF;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum JwtErrorCode implements BaseErrorCodeIF {

    NOT_FOUND(HttpStatus.NOT_FOUND, "알수 없는 토큰입니다."),
    INVALID(HttpStatus.NOT_ACCEPTABLE, "유효하지 않은 토큰입니다.")
    ;
    private final HttpStatus errorStatus;
    private final String errorMessage;

    JwtErrorCode(HttpStatus errorStatus, String errorMessage) {
        this.errorStatus = errorStatus;
        this.errorMessage = errorMessage;
    }

}
