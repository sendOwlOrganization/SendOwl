package com.example.sendowl.domain.comment.exception;

import com.example.sendowl.common.exception.BaseException;
import com.example.sendowl.common.exception.enums.BaseErrorCodeIF;

public class CommentNotFoundException extends BaseException {

    public CommentNotFoundException(BaseErrorCodeIF errorCode) {
        super(errorCode);
    }

    public CommentNotFoundException(BaseErrorCodeIF errorCode, Throwable throwable) {
        super(errorCode, throwable);
    }

}
