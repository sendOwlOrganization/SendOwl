package com.example.sendowl.domain.blame.exception;

import com.example.sendowl.common.exception.BaseException;
import com.example.sendowl.common.exception.enums.BaseErrorCodeIF;

public class BlameTypeNotFoundException extends BaseException {
    public BlameTypeNotFoundException(BaseErrorCodeIF errorCode) {
        super(errorCode);
    }

    public BlameTypeNotFoundException(BaseErrorCodeIF errorCode, Throwable throwable) {
        super(errorCode, throwable);
    }
}
