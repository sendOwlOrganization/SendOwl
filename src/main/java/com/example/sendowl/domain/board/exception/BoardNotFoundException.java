package com.example.sendowl.domain.board.exception;

import com.example.sendowl.common.exception.BaseException;
import com.example.sendowl.common.exception.enums.BaseErrorCodeIF;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class BoardNotFoundException extends BaseException {
    public BoardNotFoundException(BaseErrorCodeIF errorCode) {
        super(errorCode);
    }

    public BoardNotFoundException(BaseErrorCodeIF errorCode, Throwable throwable) {
        super(errorCode, throwable);
    }
}
