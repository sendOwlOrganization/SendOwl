package com.example.sendowl.domain.comment.exception.handler;

import com.example.sendowl.common.dto.BaseErrorResponseDto;
import com.example.sendowl.domain.user.exception.UserNotFoundException;
import com.example.sendowl.domain.user.exception.UserNotValidException;
import com.example.sendowl.domain.user.exception.enums.UserErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
public class CommentExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class) // 어떤 에러일때 핸들링 할것인지 명시한다.
    public final ResponseEntity<BaseErrorResponseDto> handleUserNotFoundExceptions(Exception ex, WebRequest request) {
        UserNotFoundException boxedException = new UserNotFoundException(
                UserErrorCode.NOT_FOUND, ex);
        return new ResponseEntity<>(BaseErrorResponseDto.of(boxedException),
                boxedException.getErrorStatus());
    }

    @ExceptionHandler(UserNotValidException.class)
    public final ResponseEntity<BaseErrorResponseDto> handleUserNotValidExceptions(Exception ex, WebRequest request) {
        UserNotValidException boxedException = new UserNotValidException(
                UserErrorCode.NOT_FOUND, ex);
        return new ResponseEntity<>(BaseErrorResponseDto.of(boxedException),
                boxedException.getErrorStatus());
    }
}
