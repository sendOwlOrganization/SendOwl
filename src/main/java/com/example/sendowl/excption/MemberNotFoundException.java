package com.example.sendowl.excption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MemberNotFoundException extends RuntimeException{
    public MemberNotFoundException(String msg) {
        super(msg);
    }
}
