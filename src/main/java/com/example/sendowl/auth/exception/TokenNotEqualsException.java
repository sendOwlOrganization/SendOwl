package com.example.sendowl.auth.exception;


import com.example.sendowl.common.exception.BaseException;
import com.example.sendowl.common.exception.enums.BaseErrorCodeIF;

public class TokenNotEqualsException extends BaseException {
    public TokenNotEqualsException(BaseErrorCodeIF errorCode) {
        super(errorCode);
    }

    public TokenNotEqualsException(BaseErrorCodeIF errorCode, Throwable throwable) {
        super(errorCode, throwable);
    }
}
