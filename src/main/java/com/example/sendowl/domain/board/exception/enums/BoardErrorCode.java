package com.example.sendowl.domain.board.exception.enums;

import com.example.sendowl.common.exception.enums.BaseErrorCodeIF;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BoardErrorCode implements BaseErrorCodeIF {

    NOT_FOUND(HttpStatus.NOT_FOUND, "등록되지 않은 게시글입니다."),
    ;
    private final HttpStatus errorStatus;
    private final String errorMessage;

    BoardErrorCode(HttpStatus errorStatus, String errorMessage) {
        this.errorStatus = errorStatus;
        this.errorMessage = errorMessage;
    }

}
