package com.example.sendowl.auth.exception.handler;

import com.example.sendowl.auth.exception.JwtInvalidException;
import com.example.sendowl.auth.exception.enums.TokenErrorCode;
import com.example.sendowl.common.dto.BaseErrorResponseDto;
import com.example.sendowl.common.exception.BaseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice(basePackages = "com.example.sendowl.api.controller")
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    // ResponseEntityExceptionHandler는 스프링 프레임워크에서 제공하는 기본적인 예외 처리 클래스

    // @PreAuthorize 의 예외를 핸들링하기 위한 클래스
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<BaseErrorResponseDto> handleAccessDeniedException(Exception ex, WebRequest request) {
        JwtInvalidException boxedException = new JwtInvalidException(
                TokenErrorCode.FORBIDDEN, ex);
        return new ResponseEntity<>(BaseErrorResponseDto.of(boxedException),
                boxedException.getErrorStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> validErrors = new HashMap<>();
        ex.getBindingResult().getAllErrors()
                .forEach(c -> validErrors.put(((FieldError) c).getField(), c.getDefaultMessage()));

        BaseException boxedException = new BaseException(status, ex.getMessage(), ex.getCause());
        return new ResponseEntity<>(
                BaseErrorResponseDto.ofMap(boxedException, validErrors),
                boxedException.getErrorStatus());
    }
}
