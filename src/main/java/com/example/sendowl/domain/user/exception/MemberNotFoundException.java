package com.example.sendowl.domain.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class MemberNotFoundException extends RuntimeException{
    public MemberNotFoundException(String msg) {
        super(msg);
    }
}
