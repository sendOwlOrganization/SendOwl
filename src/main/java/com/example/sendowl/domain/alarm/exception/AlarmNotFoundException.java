package com.example.sendowl.domain.alarm.exception;

import com.example.sendowl.common.exception.BaseException;
import com.example.sendowl.common.exception.enums.BaseErrorCodeIF;

public class AlarmNotFoundException extends BaseException {
    public AlarmNotFoundException(BaseErrorCodeIF errorCode) {
        super(errorCode);
    }

    public AlarmNotFoundException(BaseErrorCodeIF errorCode, Throwable throwable) {
        super(errorCode, throwable);
    }

}
