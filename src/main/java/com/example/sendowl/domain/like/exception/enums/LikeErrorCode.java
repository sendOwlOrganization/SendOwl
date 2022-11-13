package com.example.sendowl.domain.like.exception.enums;

import com.example.sendowl.common.exception.enums.BaseErrorCodeIF;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum LikeErrorCode implements BaseErrorCodeIF {

    NOT_FOUND(HttpStatus.BAD_REQUEST, "등록되지 않은 좋아요 입니다."),
    NO_AUTH(HttpStatus.UNAUTHORIZED, "좋아요 에 대한 권한이 없습니다."),
    ;
    private final HttpStatus errorStatus;
    private final String errorMessage;

    LikeErrorCode(HttpStatus errorStatus, String errorMessage) {
        this.errorStatus = errorStatus;
        this.errorMessage = errorMessage;
    }

}
