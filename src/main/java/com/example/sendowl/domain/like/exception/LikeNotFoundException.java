package com.example.sendowl.domain.like.exception;

import com.example.sendowl.common.exception.BaseException;
import com.example.sendowl.common.exception.enums.BaseErrorCodeIF;

public class LikeNotFoundException extends BaseException {
    public LikeNotFoundException(BaseErrorCodeIF errorCode) {
        super(errorCode);
    }

    public LikeNotFoundException(BaseErrorCodeIF errorCode, Throwable throwable) {
        super(errorCode, throwable);
    }
}
