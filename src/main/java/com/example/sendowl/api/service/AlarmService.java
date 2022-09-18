package com.example.sendowl.api.service;

import com.example.sendowl.domain.alarm.entity.Alarm;
import com.example.sendowl.domain.alarm.repository.AlarmChkRepository;
import com.example.sendowl.domain.alarm.repository.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.sendowl.domain.alarm.dto.AlarmDto.*;
import com.example.sendowl.domain.alarm.dto.AlarmChkDto.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final AlarmChkRepository alarmChkRepository;

    // 전체 알림 등록
    public void insertAlarm(AlarmReq rq) {
        Alarm alarm = rq.toEntity();
        alarmRepository.save(alarm);
    }

    // 전체 알림 수정
    public void updateAlarm(AlarmUdtReq rq) {
    }

    // 전체 알림 삭제 (soft Delete)
    public void deleteAlarm(Long id) {
    }

    //
    public void insertAlarmChk(AlarmChkReq rq) {
    }
}
