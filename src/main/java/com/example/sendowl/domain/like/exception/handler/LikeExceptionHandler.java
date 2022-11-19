package com.example.sendowl.domain.like.exception.handler;


import com.example.sendowl.common.dto.BaseErrorResponseDto;
import com.example.sendowl.domain.like.exception.LikeNotFoundException;
import com.example.sendowl.domain.like.exception.enums.LikeErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
public class LikeExceptionHandler {

    @ExceptionHandler(LikeNotFoundException.class) // 어떤 에러일때 핸들링 할것인지 명시한다.
    public final ResponseEntity<BaseErrorResponseDto> handleBoardNotFoundExceptions(Exception ex, WebRequest request) {
        LikeNotFoundException boxedException = new LikeNotFoundException(
                LikeErrorCode.NOT_FOUND, ex);
        return new ResponseEntity<>(BaseErrorResponseDto.of(boxedException),
                boxedException.getErrorStatus());
    }
}
