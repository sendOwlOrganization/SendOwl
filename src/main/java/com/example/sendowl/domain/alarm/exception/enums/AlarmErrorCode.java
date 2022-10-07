package com.example.sendowl.domain.alarm.exception.enums;

import com.example.sendowl.common.exception.enums.BaseErrorCodeIF;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum AlarmErrorCode implements BaseErrorCodeIF {

    TYPEALREADYEXIST(HttpStatus.BAD_REQUEST, "이미 등록된 알람 타입입니다."),
    TYPENOTFOUND(HttpStatus.NOT_FOUND, "등록되지 않은 알람 타입입니다."),
    NOTFOUND(HttpStatus.NOT_FOUND, "삭제되었거나 등록되지 않은 알람입니다."),
    ;
    private final HttpStatus errorStatus;
    private final String errorMessage;

    AlarmErrorCode(HttpStatus errorStatus, String errorMessage) {
        this.errorStatus = errorStatus;
        this.errorMessage = errorMessage;
    }

}
