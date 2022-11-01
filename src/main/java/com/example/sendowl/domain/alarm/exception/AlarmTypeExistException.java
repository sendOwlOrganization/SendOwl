package com.example.sendowl.domain.alarm.exception;

import com.example.sendowl.common.exception.BaseException;
import com.example.sendowl.common.exception.enums.BaseErrorCodeIF;

public class AlarmTypeExistException extends BaseException {
    public AlarmTypeExistException(BaseErrorCodeIF errorCode) {
        super(errorCode);
    }

    public AlarmTypeExistException(BaseErrorCodeIF errorCode, Throwable throwable) {
        super(errorCode, throwable);
    }
}
