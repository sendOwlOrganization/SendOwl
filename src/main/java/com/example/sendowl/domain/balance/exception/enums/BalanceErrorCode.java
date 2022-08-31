package com.example.sendowl.domain.balance.exception.enums;

import com.example.sendowl.common.exception.enums.BaseErrorCodeIF;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BalanceErrorCode implements BaseErrorCodeIF {

<<<<<<< HEAD
    NOTFOUND(HttpStatus.NOT_FOUND, "등록되지 않은 밸런스입니다."),
=======
    ALREADYEXIST(HttpStatus.BAD_REQUEST, "이미 등록된 신고 타입입니다."),
    NOTFOUND(HttpStatus.NOT_FOUND, "등록되지 않은 신고 타입입니다."),
>>>>>>> 799c1e2 (feat: BalanceController)
    ;
    private final HttpStatus errorStatus;
    private final String errorMessage;

    BalanceErrorCode(HttpStatus errorStatus, String errorMessage) {
        this.errorStatus = errorStatus;
        this.errorMessage = errorMessage;
    }

}
