package com.example.sendowl.api.oauth.exception.enums;

import com.example.sendowl.common.exception.enums.BaseErrorCodeIF;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum Oauth2ErrorCode implements BaseErrorCodeIF {

    BAD_TRANSACTIONID(HttpStatus.BAD_REQUEST, "존재하지 않는 trasactionId입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "유효하지 않은 Token입니다."),
    ;
    private final HttpStatus errorStatus;
    private final String errorMessage;

    Oauth2ErrorCode(HttpStatus errorStatus, String errorMessage) {
        this.errorStatus = errorStatus;
        this.errorMessage = errorMessage;
    }

}
