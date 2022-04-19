package com.example.sendowl.domain.category.exception;

import com.example.sendowl.common.exception.BaseException;
import com.example.sendowl.common.exception.enums.BaseErrorCodeIF;

public class CategoryNotFoundException extends BaseException {

    public CategoryNotFoundException(BaseErrorCodeIF errorCode) {
        super(errorCode);
    }

    public CategoryNotFoundException(BaseErrorCodeIF errorCode, Throwable throwable) {
        super(errorCode, throwable);
    }

}
