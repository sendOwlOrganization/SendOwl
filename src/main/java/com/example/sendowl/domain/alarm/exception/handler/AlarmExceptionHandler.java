package com.example.sendowl.domain.alarm.exception.handler;


import com.example.sendowl.common.dto.BaseErrorResponseDto;
import com.example.sendowl.domain.alarm.exception.AlarmNotFoundException;
import com.example.sendowl.domain.alarm.exception.AlarmTypeNotFoundException;
import com.example.sendowl.domain.alarm.exception.enums.AlarmErrorCode;
import com.example.sendowl.domain.board.exception.BoardNotFoundException;
import com.example.sendowl.domain.board.exception.enums.BoardErrorCode;
import com.example.sendowl.domain.comment.exception.CommentNoPermission;
import com.example.sendowl.domain.comment.exception.CommentNotFoundException;
import com.example.sendowl.domain.comment.exception.enums.CommentErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
public class AlarmExceptionHandler {

    @ExceptionHandler(AlarmNotFoundException.class)
    public final ResponseEntity<BaseErrorResponseDto> handleBoardNotFoundExceptions(Exception ex, WebRequest request) {
        AlarmNotFoundException boxedException = new AlarmNotFoundException(
                AlarmErrorCode.NOTFOUND, ex);
        return new ResponseEntity<>(BaseErrorResponseDto.of(boxedException),
                boxedException.getErrorStatus());
    }
    @ExceptionHandler(AlarmTypeNotFoundException.class)
    public final ResponseEntity<BaseErrorResponseDto> handleCommentNoPermission(Exception ex, WebRequest request) {
        AlarmTypeNotFoundException boxedException = new AlarmTypeNotFoundException(
                AlarmErrorCode.TYPEALREADYEXIST, ex);
        return new ResponseEntity<>(BaseErrorResponseDto.of(boxedException),
                boxedException.getErrorStatus());
    }
}
