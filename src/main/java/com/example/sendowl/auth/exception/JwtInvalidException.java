package com.example.sendowl.auth.exception;


import com.example.sendowl.common.exception.BaseException;
import com.example.sendowl.common.exception.enums.BaseErrorCodeIF;

public class JwtInvalidException extends BaseException {
    public JwtInvalidException(BaseErrorCodeIF errorCode) {super(errorCode);}

    public JwtInvalidException(BaseErrorCodeIF errorCode, Throwable throwable) {
        super(errorCode, throwable);
    }
}
