package com.example.sendowl.domain.blame.exception;

import com.example.sendowl.common.exception.BaseException;
import com.example.sendowl.common.exception.enums.BaseErrorCodeIF;

public class BlameTypeExistException extends BaseException {
    public BlameTypeExistException(BaseErrorCodeIF errorCode) {
        super(errorCode);
    }

    public BlameTypeExistException(BaseErrorCodeIF errorCode, Throwable throwable) {
        super(errorCode, throwable);
    }
}
