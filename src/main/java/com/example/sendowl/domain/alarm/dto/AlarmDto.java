package com.example.sendowl.domain.alarm.dto;

import com.example.sendowl.domain.alarm.entity.Alarm;
import com.example.sendowl.domain.blame.entity.Blame;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
