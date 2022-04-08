package com.example.sendowl.excption;

public class RedisBoardNotFoundException extends RuntimeException{
    public RedisBoardNotFoundException(String msg) {
        super(msg);
    }
}
