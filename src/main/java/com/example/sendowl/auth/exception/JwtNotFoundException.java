package com.example.sendowl.auth.exception;


import com.example.sendowl.common.exception.BaseException;
import com.example.sendowl.common.exception.enums.BaseErrorCodeIF;

public class JwtNotFoundException extends BaseException {
    public JwtNotFoundException(BaseErrorCodeIF errorCode) {
        super(errorCode);
    }

    public JwtNotFoundException(BaseErrorCodeIF errorCode, Throwable throwable) {
        super(errorCode, throwable);
    }
}
