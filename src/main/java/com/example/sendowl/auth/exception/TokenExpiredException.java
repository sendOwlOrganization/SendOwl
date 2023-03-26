package com.example.sendowl.auth.exception;


import com.example.sendowl.common.exception.BaseException;
import com.example.sendowl.common.exception.enums.BaseErrorCodeIF;

public class TokenExpiredException extends BaseException {
    public TokenExpiredException(BaseErrorCodeIF errorCode) {
        super(errorCode);
    }

    public TokenExpiredException(BaseErrorCodeIF errorCode, Throwable throwable) {
        super(errorCode, throwable);
    }
}
