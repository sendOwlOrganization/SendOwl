package com.example.sendowl.domain.category.exception;

import com.example.sendowl.common.exception.BaseException;
import com.example.sendowl.common.exception.enums.BaseErrorCodeIF;

public class CategoryNameAlreadyExistsException extends BaseException {

    public CategoryNameAlreadyExistsException(BaseErrorCodeIF errorCode) {
        super(errorCode);
    }

    public CategoryNameAlreadyExistsException(BaseErrorCodeIF errorCode, Throwable throwable) {
        super(errorCode, throwable);
    }

}
