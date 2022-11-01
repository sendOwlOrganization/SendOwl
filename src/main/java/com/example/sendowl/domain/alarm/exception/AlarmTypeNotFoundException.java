package com.example.sendowl.domain.alarm.exception;

import com.example.sendowl.common.exception.BaseException;
import com.example.sendowl.common.exception.enums.BaseErrorCodeIF;

public class AlarmTypeNotFoundException extends BaseException {
    public AlarmTypeNotFoundException(BaseErrorCodeIF errorCode) {
        super(errorCode);
    }

    public AlarmTypeNotFoundException(BaseErrorCodeIF errorCode, Throwable throwable) {
        super(errorCode, throwable);
    }

}
