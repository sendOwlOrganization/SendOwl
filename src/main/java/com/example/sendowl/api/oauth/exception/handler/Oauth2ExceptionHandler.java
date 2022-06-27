package com.example.sendowl.api.oauth.exception.handler;

import com.example.sendowl.api.oauth.exception.enums.UserErrorCode;
import com.example.sendowl.common.dto.BaseErrorResponseDto;
import com.example.sendowl.domain.user.dto.UserDto;
import com.example.sendowl.domain.user.exception.UserException.UserAlreadyExistException;
import com.example.sendowl.domain.user.exception.UserException.UserNotFoundException;
import com.example.sendowl.domain.user.exception.UserException.UserNotValidException;
import com.example.sendowl.domain.user.exception.UserException.UserVerifyTokenExpiredException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
public class Oauth2ExceptionHandler {

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

    @ExceptionHandler(UserAlreadyExistException.class)
    public final ResponseEntity<BaseErrorResponseDto> handleUserAlreadyExistException(Exception ex, WebRequest request) {
        UserAlreadyExistException boxedException = new UserAlreadyExistException(
                UserErrorCode.EXISTING_EMAIL, ex);
        return new ResponseEntity<>(BaseErrorResponseDto.of(boxedException),
                boxedException.getErrorStatus());
    }
    @ExceptionHandler(UserVerifyTokenExpiredException.class)
    public final ResponseEntity<BaseErrorResponseDto> handleUserVerifyTokenExpiredException(Exception ex, WebRequest request) {
        UserDto.LoginReq req = (UserDto.LoginReq) request;
        UserVerifyTokenExpiredException boxedException = new UserVerifyTokenExpiredException(
                UserErrorCode.EXPIRED_VERIFICATION_TOKEN, ex);
        return new ResponseEntity<>(BaseErrorResponseDto.of(boxedException, req.getEmail()),
                boxedException.getErrorStatus());
    }
}
