package com.example.sendowl.domain.alarm.entity;

import com.example.sendowl.common.entity.BaseEntity;
import com.example.sendowl.domain.category.entity.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Alarm extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_id")
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private AlarmType alarmType;

    public void insertAlarm(String content, AlarmType alarmType) {
        this.content = content;
        this.alarmType = alarmType;
    }

    public void updateAlarm(String content, AlarmType alarmType) {
        this.content = content;
        this.alarmType = alarmType;
    }
}
