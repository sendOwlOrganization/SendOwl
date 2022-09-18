package com.example.sendowl.api.controller;

import com.example.sendowl.api.service.AlarmService;
import com.example.sendowl.api.service.BlameService;
import com.example.sendowl.domain.alarm.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/alarm")
public class AlarmController {
    private final AlarmService alarmService;

    @Operation(summary = "알림 등록", description = "전체 알림 및 개별 알림 등록")
    @PostMapping(path = "")
    public ResponseEntity<?> alarm(final @Valid @RequestBody AlarmReq rq){
        //alarmService.insertAlarm(rq);

        return new ResponseEntity(null, HttpStatus.OK);
    }

    @Operation(summary = "전체 알림 수정", description = "관리자 -> 전체 알림 수정")
    @PutMapping(path = "/{id}")
    public ResponseEntity<?> insertBlameType(final @Valid @RequestBody BlameDto.BlameTypeUpdateReq rq){
        blameService.updateBlameType(rq);
        return new ResponseEntity(null,HttpStatus.OK);
    }

    @Operation(summary = "알림 확인", description = "알림 확인 시, 알림체크 테이블에 등록")
    @PostMapping(path = "/{id}")
    public ResponseEntity<?> insertAlarmChk(final @Valid @RequestBody BlameDto.BlameTypeReq rq){
        blameService.insertBlameType(rq);
        return new ResponseEntity(null,HttpStatus.OK);
    }

    @Operation(summary = "신고 종류 제거", description = "신고 가능한 종류 중 하나를 삭제한다.")
    @DeleteMapping(path = "/type/{id}")
    public ResponseEntity<?> insertBlameType(final @PathVariable Long id){
        blameService.deleteBlameType(id);
        return new ResponseEntity(null,HttpStatus.OK);
    }

}
