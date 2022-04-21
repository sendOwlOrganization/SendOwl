package com.example.sendowl.auth.exception.handler;


import com.example.sendowl.auth.exception.JwtInvalidException;
import com.example.sendowl.auth.exception.JwtNotFoundException;
import com.example.sendowl.auth.exception.enums.JwtErrorCode;
import com.example.sendowl.common.dto.BaseErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


//@ControllerAdvice // 컨트롤러에 가기 전에 발생되는 예외라 어떻게 처리하지?
@Component
public class JwtExceptionHandler {

    //@ExceptionHandler(JwtNotFoundException.class) // 어떤 에러일때 핸들링 할것인지 명시한다.
    public final ResponseEntity<BaseErrorResponseDto> handleJwtNotFoundExceptions(Exception ex, WebRequest request) {
        JwtNotFoundException boxedException = new JwtNotFoundException(
                JwtErrorCode.NOT_FOUND, ex);
        return new ResponseEntity<>(BaseErrorResponseDto.of(boxedException),
                boxedException.getErrorStatus());
    }

    //@ExceptionHandler(JwtInvalidException.class) // 어떤 에러일때 핸들링 할것인지 명시한다.
    public final ResponseEntity<BaseErrorResponseDto> handleJwtInvalidExceptions(Exception ex, WebRequest request) {
        JwtInvalidException boxedException = new JwtInvalidException(
                JwtErrorCode.INVALID, ex);
        return new ResponseEntity<>(BaseErrorResponseDto.of(boxedException),
                boxedException.getErrorStatus());
    }
}
