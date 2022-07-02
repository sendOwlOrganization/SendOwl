package com.example.sendowl.api.oauth.exception;

import com.example.sendowl.common.exception.BaseException;
import com.example.sendowl.common.exception.enums.BaseErrorCodeIF;

public class Oauth2Exception {

    public static class TransactionIdNotValid extends BaseException {
        public TransactionIdNotValid(BaseErrorCodeIF errorCode) {super(errorCode);        }
        public TransactionIdNotValid(BaseErrorCodeIF errorCode, Throwable throwable) {            super(errorCode, throwable);        }
    }
    public static class TokenNotValid extends BaseException {
        public TokenNotValid(BaseErrorCodeIF errorCode) {super(errorCode);        }
        public TokenNotValid(BaseErrorCodeIF errorCode, Throwable throwable) {            super(errorCode, throwable);        }
    }
}
