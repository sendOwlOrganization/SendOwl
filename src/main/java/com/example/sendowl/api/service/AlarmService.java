package com.example.sendowl.api.service;

import com.example.sendowl.domain.alarm.dto.AlarmDto.AlarmReq;
import com.example.sendowl.domain.alarm.dto.AlarmDto.AlarmUdtReq;
import com.example.sendowl.domain.alarm.entity.Alarm;
import com.example.sendowl.domain.alarm.entity.AlarmChk;
import com.example.sendowl.domain.alarm.entity.AlarmType;
import com.example.sendowl.domain.alarm.exception.AlarmNotFoundException;
import com.example.sendowl.domain.alarm.exception.AlarmTypeNotFoundException;
import com.example.sendowl.domain.alarm.repository.AlarmChkRepository;
import com.example.sendowl.domain.alarm.repository.AlarmRepository;
import com.example.sendowl.domain.alarm.repository.AlarmTypeRepository;
import com.example.sendowl.domain.board.exception.enums.BoardErrorCode;
import com.example.sendowl.domain.tag.enums.TagErrorCode;
import com.example.sendowl.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
//@Transactional(readOnly = true)
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final AlarmChkRepository alarmChkRepository;
    private final AlarmTypeRepository alarmTypeRepository;

    // 알림 등록
    public void insertAlarm(AlarmReq rq) {
        AlarmType alarmType = alarmTypeRepository.findById(rq.getTypeId()).orElseThrow(
                () -> new AlarmTypeNotFoundException(TagErrorCode.NOT_FOUND));
        Alarm alarm = new Alarm();
        alarm.insertAlarm(rq.getContent(), alarmType);
        alarmRepository.save(alarm);
    }

    // 전체 알림 수정
    public void updateAlarm(AlarmUdtReq rq) {
        Alarm alarm = alarmRepository.findById(rq.getId()).orElseThrow(
                () -> new AlarmNotFoundException(BoardErrorCode.NOT_FOUND));

        AlarmType alarmType = alarmTypeRepository.findById(rq.getTypeId()).orElseThrow(
                () -> new AlarmTypeNotFoundException(TagErrorCode.NOT_FOUND));

        alarm.updateAlarm(rq.getContent(), alarmType);
        alarmRepository.save(alarm);
    }

    // 전체 알림 삭제 (soft Delete)
    public void deleteAlarm(Long id) {
        Alarm alarm = alarmRepository.findById(id).orElseThrow(
                () -> new AlarmNotFoundException(BoardErrorCode.NOT_FOUND));

        alarm.delete();

        alarmRepository.save(alarm);
    }

    // 유저 별 알람 확인
    public void checkAlarm(Long alarmId, User user) {
        Alarm alarm = alarmRepository.findById(alarmId).orElseThrow(
                () -> new AlarmNotFoundException(BoardErrorCode.NOT_FOUND));

        AlarmChk alarmChk = new AlarmChk();
        alarmChk.insertAlarmChk(alarm, user);

        alarmChkRepository.save(alarmChk);
    }
}
