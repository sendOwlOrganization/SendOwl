package com.example.sendowl.domain.board.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class BoardNotFoundException extends RuntimeException{
    public BoardNotFoundException(String msg) {
        super(msg);
    }
}
