package com.example.sendowl.common.exception.enums;

import org.springframework.http.HttpStatus;

public interface BaseErrorCodeIF {

    HttpStatus getErrorStatus();

    String getErrorMessage();
}
