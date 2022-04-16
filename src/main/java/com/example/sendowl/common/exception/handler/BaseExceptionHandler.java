package com.example.sendowl.common.exception.handler;

import com.example.sendowl.common.dto.BaseErrorResponseDto;
import com.example.sendowl.common.exception.BaseException;
import com.example.sendowl.common.exception.enums.BaseErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler({BaseException.class})
    @ResponseBody
    public ResponseEntity<BaseErrorResponseDto> handleCommonException(
            BaseException e, HttpServletRequest request) {
        return new ResponseEntity<>(BaseErrorResponseDto.of(e), e.getErrorStatus());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<BaseErrorResponseDto> handleException(
            Exception e, HttpServletRequest request) {

        BaseException boxedException = new BaseException(BaseErrorCode.UNKNOWN_SERVER_ERROR, e);
        return new ResponseEntity<>(
                BaseErrorResponseDto.of(boxedException),
                boxedException.getErrorStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseErrorResponseDto> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> validErrors = new HashMap<>();
        ex.getBindingResult().getAllErrors()
                .forEach(c -> validErrors.put(((FieldError) c).getField(), c.getDefaultMessage()));

        BaseException boxedException = new BaseException(BaseErrorCode.INVALID_PARAMS_ERROR, ex);
        return new ResponseEntity<>(
                BaseErrorResponseDto.ofMap(boxedException, validErrors),
                boxedException.getErrorStatus());
    }
}
