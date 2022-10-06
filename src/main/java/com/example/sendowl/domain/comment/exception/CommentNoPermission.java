package com.example.sendowl.domain.comment.exception;

import com.example.sendowl.common.exception.BaseException;
import com.example.sendowl.common.exception.enums.BaseErrorCodeIF;

public class CommentNoPermission extends BaseException {

    public CommentNoPermission(BaseErrorCodeIF errorCode) {
        super(errorCode);
    }

    public CommentNoPermission(BaseErrorCodeIF errorCode, Throwable throwable) {
        super(errorCode, throwable);
    }

}
