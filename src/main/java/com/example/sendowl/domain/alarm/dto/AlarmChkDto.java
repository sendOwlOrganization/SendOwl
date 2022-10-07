package com.example.sendowl.domain.alarm.dto;

import com.example.sendowl.domain.board.dto.BoardDto;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AlarmChkDto {
    @Getter
    public static class AlarmChkReq {
        @NotNull(message = "존재하지 않는 알람입니다.")
        private Long alarmId;

    }
}
