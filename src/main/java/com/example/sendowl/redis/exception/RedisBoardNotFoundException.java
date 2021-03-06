package com.example.sendowl.redis.exception;

import com.example.sendowl.common.exception.BaseException;
import com.example.sendowl.common.exception.enums.BaseErrorCodeIF;

public class RedisBoardNotFoundException extends BaseException {
    public RedisBoardNotFoundException(BaseErrorCodeIF errorCode) {
        super(errorCode);
    }

    public RedisBoardNotFoundException(BaseErrorCodeIF errorCode, Throwable throwable) {
        super(errorCode, throwable);
    }
}
