package com.example.sendowl.domain.alarm.dto;

import com.example.sendowl.domain.alarm.entity.Alarm;
import lombok.Getter;

public class AlarmDto {
    @Getter
    public static class AlarmReq {
        private String content;
        private String type;
        public Alarm toEntity(){
            return Alarm.builder()
                    .content(content)
                    .type(type).build();
        }
    }

    @Getter
    public static class AlarmUdtReq {

    }


}
