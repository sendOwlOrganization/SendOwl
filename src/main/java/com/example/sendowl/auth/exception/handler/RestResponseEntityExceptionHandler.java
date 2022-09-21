package com.example.sendowl.auth.exception.handler;

import com.example.sendowl.auth.exception.JwtInvalidException;
import com.example.sendowl.auth.exception.enums.JwtErrorCode;
import com.example.sendowl.common.dto.BaseErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    // @PreAuthorize 의 예외를 핸들링하기 위한 클래스
    // ResponseEntityExceptionHandler 은 spring mvc에서 발생하는 전반적인 예외들을 handling 한다.

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<BaseErrorResponseDto> handleAccessDeniedException(Exception ex, WebRequest request) {
        JwtInvalidException boxedException = new JwtInvalidException(
                JwtErrorCode.FORBIDDEN, ex);
        return new ResponseEntity<>(BaseErrorResponseDto.of(boxedException),
                boxedException.getErrorStatus());
    }
}
