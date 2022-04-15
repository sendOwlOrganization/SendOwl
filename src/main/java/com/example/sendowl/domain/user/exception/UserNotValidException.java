package com.example.sendowl.domain.user.exception;

import com.example.sendowl.common.exception.BaseException;
import com.example.sendowl.common.exception.enums.BaseErrorCodeIF;

public class UserNotValidException extends BaseException {

    public UserNotValidException(BaseErrorCodeIF errorCode) {
        super(errorCode);
    }

    public UserNotValidException(BaseErrorCodeIF errorCode, Throwable throwable) {
        super(errorCode, throwable);
    }

}
