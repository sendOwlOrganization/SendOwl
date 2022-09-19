package com.example.sendowl.domain.alarm.dto;

import com.example.sendowl.domain.alarm.entity.Alarm;
import com.example.sendowl.domain.alarm.entity.AlarmType;
import com.example.sendowl.domain.alarm.repository.AlarmTypeRepository;
import com.example.sendowl.domain.category.entity.Category;
import com.example.sendowl.domain.category.enums.CategoryErrorCode;
import com.example.sendowl.domain.category.exception.CategoryNotFoundException;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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
