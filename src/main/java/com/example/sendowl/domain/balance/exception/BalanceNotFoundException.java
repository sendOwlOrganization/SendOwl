package com.example.sendowl.domain.balance.exception;

import com.example.sendowl.common.exception.BaseException;
import com.example.sendowl.common.exception.enums.BaseErrorCodeIF;

public class BalanceNotFoundException extends BaseException {
    public BalanceNotFoundException(BaseErrorCodeIF errorCode) {
        super(errorCode);
    }

    public BalanceNotFoundException(BaseErrorCodeIF errorCode, Throwable throwable) {
        super(errorCode, throwable);
    }

}
