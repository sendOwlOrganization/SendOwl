package com.example.sendowl.domain.user.exception;

import com.example.sendowl.common.exception.BaseException;
import com.example.sendowl.common.exception.enums.BaseErrorCodeIF;

public class UserException {

    public static class UserAlreadyExistException extends BaseException {
        public UserAlreadyExistException(BaseErrorCodeIF errorCode) {
            super(errorCode);
        }

        public UserAlreadyExistException(BaseErrorCodeIF errorCode, Throwable throwable) {
            super(errorCode, throwable);
        }
    }

    public static class UserNotFoundException extends BaseException {

        public UserNotFoundException(BaseErrorCodeIF errorCode) {
            super(errorCode);
        }

        public UserNotFoundException(BaseErrorCodeIF errorCode, Throwable throwable) {
            super(errorCode, throwable);
        }

    }

    public static class UserNotValidException extends BaseException {

        public UserNotValidException(BaseErrorCodeIF errorCode) {
            super(errorCode);
        }

        public UserNotValidException(BaseErrorCodeIF errorCode, Throwable throwable) {
            super(errorCode, throwable);
        }

    }

    public static class UserVerifyTokenExpiredException extends BaseException {
        public UserVerifyTokenExpiredException(BaseErrorCodeIF errorCode) {
            super(errorCode);
        }

        public UserVerifyTokenExpiredException(BaseErrorCodeIF errorCode, Throwable throwable) {
            super(errorCode, throwable);
        }
    }

    public static class UserNickNameAlreadyExistException extends BaseException {
        public UserNickNameAlreadyExistException(BaseErrorCodeIF errorCode) {
            super(errorCode);
        }

        public UserNickNameAlreadyExistException(BaseErrorCodeIF errorCode, Throwable throwable) {
            super(errorCode, throwable);
        }
    }



}
