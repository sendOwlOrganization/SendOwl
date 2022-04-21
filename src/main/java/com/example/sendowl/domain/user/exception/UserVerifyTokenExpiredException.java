package com.example.sendowl.domain.user.exception;

import com.example.sendowl.common.exception.BaseException;
import com.example.sendowl.common.exception.enums.BaseErrorCodeIF;

public class UserVerifyTokenExpiredException extends BaseException {
    public UserVerifyTokenExpiredException(BaseErrorCodeIF errorCode) {
        super(errorCode);
    }

    public UserVerifyTokenExpiredException(BaseErrorCodeIF errorCode, Throwable throwable) {
        super(errorCode, throwable);
    }
}
