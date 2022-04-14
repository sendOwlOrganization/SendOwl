package com.example.sendowl.domain.user.exception;

import com.example.sendowl.common.exception.BaseException;
import com.example.sendowl.common.exception.enums.BaseErrorCodeIF;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException(BaseErrorCodeIF errorCode) {
        super(errorCode);
    }

    public UserNotFoundException(BaseErrorCodeIF errorCode, Throwable throwable) {
        super(errorCode, throwable);
    }

}
