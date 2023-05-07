package com.example.sendowl.domain.user.exception.handler;

import com.example.sendowl.domain.user.exception.Oauth2Exception.TransactionIdNotValid;
import com.example.sendowl.domain.user.exception.enums.Oauth2ErrorCode;
import com.example.sendowl.common.dto.BaseErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
public class Oauth2ExceptionHandler {

    @ExceptionHandler(TransactionIdNotValid.class) // 어떤 에러일때 핸들링 할것인지 명시한다.
    public final ResponseEntity<BaseErrorResponseDto> handleUserNotFoundExceptions(Exception ex, WebRequest request) {
        TransactionIdNotValid boxedException = new TransactionIdNotValid(
                Oauth2ErrorCode.BAD_TRANSACTIONID, ex);
        return new ResponseEntity<>(BaseErrorResponseDto.of(boxedException),
                boxedException.getErrorStatus());
    }
}
