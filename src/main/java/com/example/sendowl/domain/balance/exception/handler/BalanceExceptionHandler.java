package com.example.sendowl.domain.balance.exception.handler;


import com.example.sendowl.common.dto.BaseErrorResponseDto;
import com.example.sendowl.domain.balance.exception.BalanceNotFoundException;
import com.example.sendowl.domain.balance.exception.enums.BalanceErrorCode;
<<<<<<< HEAD
=======
import com.example.sendowl.domain.balance.exception.enums.BlameErrorCode;
>>>>>>> 799c1e2 (feat: BalanceController)
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
public class BalanceExceptionHandler {

    @ExceptionHandler(BalanceNotFoundException.class) // 어떤 에러일때 핸들링 할것인지 명시한다.
    public final ResponseEntity<BaseErrorResponseDto> handleBoardNotFoundExceptions(Exception ex, WebRequest request) {
        BalanceNotFoundException boxedException = new BalanceNotFoundException(
                BalanceErrorCode.NOTFOUND, ex);
        return new ResponseEntity<>(BaseErrorResponseDto.of(boxedException),
                boxedException.getErrorStatus());
    }
}
