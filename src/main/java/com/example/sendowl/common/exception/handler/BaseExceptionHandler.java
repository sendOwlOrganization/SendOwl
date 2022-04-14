package com.example.sendowl.common.exception.handler;

import com.example.sendowl.common.dto.BaseErrorResponse;
import com.example.sendowl.common.exception.BaseException;
import com.example.sendowl.common.exception.enums.BaseErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler({BaseException.class})
    @ResponseBody
    public ResponseEntity<BaseErrorResponse> handleCommonException(
            BaseException e, HttpServletRequest request) {
        return new ResponseEntity<>(BaseErrorResponse.createErrorResponse(e), e.getErrorStatus());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<BaseErrorResponse> handleException(
            Exception e, HttpServletRequest request) {
        request.getSession();

        BaseException boxedException = new BaseException(BaseErrorCode.UNKNOWN_SERVER_ERROR, e);
        return new ResponseEntity<>(
                BaseErrorResponse.createErrorResponse(boxedException),
                boxedException.getErrorStatus());
    }
}
