package com.example.sendowl.domain.tag.exception;

import com.example.sendowl.common.exception.BaseException;
import com.example.sendowl.common.exception.enums.BaseErrorCodeIF;

public class TagNotFoundException extends BaseException {

    public TagNotFoundException(BaseErrorCodeIF errorCode) {
        super(errorCode);
    }

    public TagNotFoundException(BaseErrorCodeIF errorCode, Throwable throwable) {
        super(errorCode, throwable);
    }

}
