package com.example.sendowl.domain.board.exception.handler;


import com.example.sendowl.common.dto.BaseErrorResponse;
import com.example.sendowl.domain.board.exception.BoardNotFoundException;
import com.example.sendowl.domain.board.exception.enums.BoardErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
public class BoardExceptionHandler {

    @ExceptionHandler(BoardNotFoundException.class) // 어떤 에러일때 핸들링 할것인지 명시한다.
    public final ResponseEntity<BaseErrorResponse> handleBoardNotFoundExceptions(Exception ex, WebRequest request) {
        BoardNotFoundException boxedException = new BoardNotFoundException(
                BoardErrorCode.NOT_FOUND, ex);
        return new ResponseEntity<>(BaseErrorResponse.createErrorResponse(boxedException),
                boxedException.getErrorStatus());
    }
}
