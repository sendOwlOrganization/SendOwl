package com.example.sendowl.domain.category.exception;

import com.example.sendowl.common.exception.BaseException;
import com.example.sendowl.common.exception.enums.BaseErrorCodeIF;

public class CategoryNameNotFoundException extends BaseException {

    public CategoryNameNotFoundException(BaseErrorCodeIF errorCode) {
        super(errorCode);
    }

    public CategoryNameNotFoundException(BaseErrorCodeIF errorCode, Throwable throwable) {
        super(errorCode, throwable);
    }

}
