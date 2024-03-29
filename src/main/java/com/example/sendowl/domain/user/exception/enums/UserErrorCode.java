package com.example.sendowl.domain.user.exception.enums;

import com.example.sendowl.common.exception.enums.BaseErrorCodeIF;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum UserErrorCode implements BaseErrorCodeIF {

    NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."),
    INVALID_USER_INFO(HttpStatus.BAD_REQUEST, "회원 정보를 올바르게 기입해주세요. (이메일, 비밀번호, 이름)"),
    EXISTING_EMAIL(HttpStatus.BAD_REQUEST, "이미 가입된 이메일 입니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "잘못된 비밀번호 입니다."),
    EXPIRED_VERIFICATION_TOKEN(HttpStatus.INTERNAL_SERVER_ERROR, "이메일 인증 토큰이 만료되었습니다."),
    EXISTING_NICKNAME(HttpStatus.BAD_REQUEST, "중복된 닉네임입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "유저에게 권한이 없습니다."),
    ;
    private final HttpStatus errorStatus;
    private final String errorMessage;

    UserErrorCode(HttpStatus errorStatus, String errorMessage) {
        this.errorStatus = errorStatus;
        this.errorMessage = errorMessage;
    }

}
