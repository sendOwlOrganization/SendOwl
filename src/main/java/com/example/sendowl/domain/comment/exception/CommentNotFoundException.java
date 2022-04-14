package com.example.sendowl.domain.comment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class CommentNotFoundException extends RuntimeException{
    public CommentNotFoundException(String msg) {
        super(msg);
    }
}
