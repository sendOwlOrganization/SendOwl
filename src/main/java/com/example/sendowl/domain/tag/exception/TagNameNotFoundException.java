package com.example.sendowl.domain.tag.exception;

import com.example.sendowl.common.exception.BaseException;
import com.example.sendowl.common.exception.enums.BaseErrorCodeIF;

public class TagNameNotFoundException extends BaseException {

    public TagNameNotFoundException(BaseErrorCodeIF errorCode) {
        super(errorCode);
    }

    public TagNameNotFoundException(BaseErrorCodeIF errorCode, Throwable throwable) {
        super(errorCode, throwable);
    }

}
