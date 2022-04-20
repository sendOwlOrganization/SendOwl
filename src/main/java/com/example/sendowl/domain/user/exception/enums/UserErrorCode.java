package com.example.sendowl.domain.user.exception.enums;

import com.example.sendowl.common.exception.enums.BaseErrorCodeIF;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum UserErrorCode implements BaseErrorCodeIF {

    NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "존재하지 않는 유저입니다."),
    INVALID_USER_INFO(HttpStatus.INTERNAL_SERVER_ERROR, "회원 정보를 올바르게 기입해주세요. (이메일, 비밀번호, 이름)"),
    EXISTING_EMAIL(HttpStatus.INTERNAL_SERVER_ERROR, "이미 가입된 이메일 입니다."),
    INVALID_PASSWORD(HttpStatus.INTERNAL_SERVER_ERROR, "잘못된 비밀번호 입니다."),
    EXPIRED_VERIFICATION_TOKEN(HttpStatus.INTERNAL_SERVER_ERROR, "이메일 인증 토큰이 만료되었습니다."),
    ;
    private final HttpStatus errorStatus;
    private final String errorMessage;

    UserErrorCode(HttpStatus errorStatus, String errorMessage) {
        this.errorStatus = errorStatus;
        this.errorMessage = errorMessage;
    }

}
