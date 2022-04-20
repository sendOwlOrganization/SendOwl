package com.example.sendowl.domain.user.exception;

import com.example.sendowl.common.exception.BaseException;
import com.example.sendowl.common.exception.enums.BaseErrorCodeIF;

public class UserAlreadyExistException extends BaseException {
    public UserAlreadyExistException(BaseErrorCodeIF errorCode) {
        super(errorCode);
    }

    public UserAlreadyExistException(BaseErrorCodeIF errorCode, Throwable throwable) {
        super(errorCode, throwable);
    }
}
