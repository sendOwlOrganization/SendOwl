package com.example.sendowl.domain.blame.exception.enums;

import com.example.sendowl.common.exception.enums.BaseErrorCodeIF;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BlameErrorCode implements BaseErrorCodeIF {

    ALREADYEXIST(HttpStatus.BAD_REQUEST, "이미 등록된 신고 타입입니다."),
    ;
    private final HttpStatus errorStatus;
    private final String errorMessage;

    BlameErrorCode(HttpStatus errorStatus, String errorMessage) {
        this.errorStatus = errorStatus;
        this.errorMessage = errorMessage;
    }

}
