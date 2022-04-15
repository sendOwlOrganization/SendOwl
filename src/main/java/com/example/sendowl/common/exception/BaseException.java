package com.example.sendowl.common.exception;

import com.example.sendowl.common.exception.enums.BaseErrorCodeIF;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseException extends RuntimeException {

    HttpStatus errorStatus;
    int errorCode;
    String errorMessage;
    String description;
    Throwable cause;

    public BaseException(HttpStatus httpStatus, String message, Throwable throwable) {
        super(message, throwable);
        this.errorStatus = httpStatus;
        this.errorCode = httpStatus.value();
        this.errorMessage = message;
    }

    public BaseException(BaseErrorCodeIF errorCode) {
        super("[ErrorCode] : " + errorCode.getErrorStatus() + "\n"
                + "[ErrorMessage] : " + errorCode.getErrorMessage() + "\n");
        this.errorCode = errorCode.getErrorStatus().value();
        this.errorMessage = errorCode.getErrorMessage();
        this.errorStatus = errorCode.getErrorStatus();
    }


    public BaseException(BaseErrorCodeIF errorCode, String description) {
        super("[ErrorCode] : " + errorCode.getErrorStatus() + "\n"
                + "[ErrorMessage] : " + errorCode.getErrorMessage() + "\n"
                + "[Description] : " + description + "\n");
        this.errorCode = errorCode.getErrorStatus().value();
        this.description = description;
        this.errorMessage = errorCode.getErrorMessage();
        this.errorStatus = errorCode.getErrorStatus();
    }

    public BaseException(BaseErrorCodeIF errorCode, Throwable throwable) {
        super("[ErrorCode] : " + errorCode.getErrorStatus() + "\n"
                        + "[ErrorMessage] : " + errorCode.getErrorMessage() + "\n"
                , throwable);
        this.errorCode = errorCode.getErrorStatus().value();
        this.errorMessage = errorCode.getErrorMessage();
        this.errorStatus = errorCode.getErrorStatus();
        this.cause = throwable;
    }

    public BaseException(BaseErrorCodeIF errorCode, String description, Throwable throwable) {
        super("[ErrorCode] : " + errorCode.getErrorStatus() + "\n"
                        + "[ErrorMessage] : " + errorCode.getErrorMessage() + "\n"
                        + "[Description] : " + description + "\n"
                , throwable);
        this.errorCode = errorCode.getErrorStatus().value();
        this.description = description;
        this.errorMessage = errorCode.getErrorMessage();
        this.errorStatus = errorCode.getErrorStatus();
        this.cause = throwable;
    }

}