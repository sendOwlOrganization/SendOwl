package com.example.sendowl.domain.like.exception;

import com.example.sendowl.common.exception.BaseException;
import com.example.sendowl.common.exception.enums.BaseErrorCodeIF;

public class LikeNoAuthException extends BaseException {
    public LikeNoAuthException(BaseErrorCodeIF errorCode) {
        super(errorCode);
    }

    public LikeNoAuthException(BaseErrorCodeIF errorCode, Throwable throwable) {
        super(errorCode, throwable);
    }
}
