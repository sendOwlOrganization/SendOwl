package com.example.sendowl.domain.blame.exception.handler;


import com.example.sendowl.common.dto.BaseErrorResponseDto;
import com.example.sendowl.domain.blame.exception.BlameTypeExistException;
import com.example.sendowl.domain.blame.exception.enums.BlameErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
public class BlameTypeExceptionHandler {

    @ExceptionHandler(BlameTypeExistException.class) // 어떤 에러일때 핸들링 할것인지 명시한다.
    public final ResponseEntity<BaseErrorResponseDto> handleBoardNotFoundExceptions(Exception ex, WebRequest request) {
        BlameTypeExistException boxedException = new BlameTypeExistException(
                BlameErrorCode.ALREADYEXIST, ex);
        return new ResponseEntity<>(BaseErrorResponseDto.of(boxedException),
                boxedException.getErrorStatus());
    }
}
