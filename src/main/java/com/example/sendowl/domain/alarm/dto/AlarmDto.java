package com.example.sendowl.domain.alarm.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

public class AlarmDto {
    @Getter
    public static class AlarmReq {
        private String content;
        private Long typeId;
    }

    @Getter
    public static class AlarmUdtReq {
        @NotNull
        private Long id;
        private String content;
        private Long typeId;
    }


}
