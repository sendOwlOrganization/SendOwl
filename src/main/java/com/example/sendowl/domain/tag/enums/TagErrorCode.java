package com.example.sendowl.domain.tag.enums;

import com.example.sendowl.common.exception.enums.BaseErrorCodeIF;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum TagErrorCode implements BaseErrorCodeIF {

    NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 태그입니다."),
    ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 존재하는 태그입니다."),
    ;
    private final HttpStatus errorStatus;
    private final String errorMessage;

    TagErrorCode(HttpStatus errorStatus, String errorMessage) {
        this.errorStatus = errorStatus;
        this.errorMessage = errorMessage;
    }

}
