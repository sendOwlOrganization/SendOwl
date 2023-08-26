package com.example.sendowl.domain.tag.exception;

import com.example.sendowl.common.exception.BaseException;
import com.example.sendowl.common.exception.enums.BaseErrorCodeIF;

public class TagNameAlreadyExistsException extends BaseException {

    public TagNameAlreadyExistsException(BaseErrorCodeIF errorCode) {
        super(errorCode);
    }

    public TagNameAlreadyExistsException(BaseErrorCodeIF errorCode, Throwable throwable) {
        super(errorCode, throwable);
    }

}
