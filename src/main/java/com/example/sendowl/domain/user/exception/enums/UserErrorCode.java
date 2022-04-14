package com.example.sendowl.domain.user.exception.enums;

import com.example.sendowl.common.exception.enums.BaseErrorCodeIF;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum UserErrorCode implements BaseErrorCodeIF {

    NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "존재하지 않는 유저입니다."),
    NOT_VALID(HttpStatus.INTERNAL_SERVER_ERROR, "유효하지 않은 유저입니다."),
    NOT_VALID_EMAIL(HttpStatus.INTERNAL_SERVER_ERROR, "이미 가입된 이메일 입니다."),
    ;
    private final HttpStatus errorStatus;
    private final String errorMessage;

    UserErrorCode(HttpStatus errorStatus, String errorMessage) {
        this.errorStatus = errorStatus;
        this.errorMessage = errorMessage;
    }

}
