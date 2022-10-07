package com.example.sendowl.domain.alarm.entity;

import com.example.sendowl.common.entity.BaseEntity;
import com.example.sendowl.domain.alarm.dto.AlarmChkDto;
import com.example.sendowl.domain.alarm.exception.AlarmNotFoundException;
import com.example.sendowl.domain.board.exception.enums.BoardErrorCode;
import com.example.sendowl.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class AlarmChk extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alarm_id")
    private Alarm alarm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void insertAlarmChk(Alarm alarm, User user) {
        this.alarm = alarm;
        this.user = user;
    }
}
