package com.example.sendowl.domain.comment.exception.handler;

import com.example.sendowl.common.dto.BaseErrorResponseDto;
import com.example.sendowl.domain.comment.exception.CommentNotFoundException;
import com.example.sendowl.domain.comment.exception.enums.CommentErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
public class CommentExceptionHandler {

    @ExceptionHandler(CommentNotFoundException.class) // 어떤 에러일때 핸들링 할것인지 명시한다.
    public final ResponseEntity<BaseErrorResponseDto> handleCommentNotFoundException(Exception ex, WebRequest request) {
        CommentNotFoundException boxedException = new CommentNotFoundException(
                CommentErrorCode.NOT_FOUND, ex);
        return new ResponseEntity<>(BaseErrorResponseDto.of(boxedException),
                boxedException.getErrorStatus());
    }
}
