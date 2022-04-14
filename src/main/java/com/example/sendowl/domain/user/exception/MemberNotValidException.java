package com.example.sendowl.domain.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MemberNotValidException extends RuntimeException{
    public MemberNotValidException(String msg) {
        super(msg);
    }
}
