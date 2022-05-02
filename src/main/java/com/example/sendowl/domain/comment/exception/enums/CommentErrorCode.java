package com.example.sendowl.domain.comment.exception.enums;

import com.example.sendowl.common.exception.enums.BaseErrorCodeIF;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CommentErrorCode implements BaseErrorCodeIF {

    NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "등록되지 않은 댓글입니다."),
    NOT_FOUND_PARENT(HttpStatus.INTERNAL_SERVER_ERROR, "상위 댓글이 존재하지 않습니다."),
    ;
    private final HttpStatus errorStatus;
    private final String errorMessage;

    CommentErrorCode(HttpStatus errorStatus, String errorMessage) {
        this.errorStatus = errorStatus;
        this.errorMessage = errorMessage;
    }

}
