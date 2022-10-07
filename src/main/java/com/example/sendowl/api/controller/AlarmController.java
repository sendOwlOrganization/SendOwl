package com.example.sendowl.api.controller;

import com.example.sendowl.api.service.AlarmService;
import com.example.sendowl.api.service.BlameService;
import com.example.sendowl.auth.PrincipalDetails;
import com.example.sendowl.domain.alarm.dto.AlarmDto.*;
import com.example.sendowl.domain.alarm.dto.AlarmChkDto.*;
import com.example.sendowl.domain.alarm.entity.Alarm;
import com.example.sendowl.domain.alarm.entity.AlarmChk;
import com.example.sendowl.domain.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/alarm")
public class AlarmController {
    private final AlarmService alarmService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "전체 알림 등록", description = "전체 알림 및 개별 알림 등록", security = { @SecurityRequirement(name = "bearerAuth") })
    @PostMapping(path = "")
    public ResponseEntity<?> insertAlarm(final @Valid @RequestBody AlarmReq rq){
        alarmService.insertAlarm(rq);

        return new ResponseEntity(null, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "전체 알림 수정", description = "관리자 -> 전체 알림 수정", security = { @SecurityRequirement(name = "bearerAuth") })
    @PutMapping(path = "/content")
    public ResponseEntity<?> updateAlarm(final @Valid @RequestBody AlarmUdtReq rq){
        alarmService.updateAlarm(rq);
        return new ResponseEntity(null,HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "전체 알림 삭제", description = "관리자 -> 전체 알림 삭제", security = { @SecurityRequirement(name = "bearerAuth") })
    @PutMapping(path = "/{id}")
    public ResponseEntity<?> deleteAlarm(final @PathVariable Long id){
        alarmService.deleteAlarm(id);
        return new ResponseEntity(null,HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "알림 확인", description = "알림 확인 시, 알림체크 테이블에 등록", security = { @SecurityRequirement(name = "bearerAuth") })
    @PostMapping(path = "/{id}")
    public ResponseEntity<?> insertAlarmChk(final @Valid @RequestBody AlarmChkReq rq){
        PrincipalDetails principal = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = principal.getUser();

        alarmService.checkAlarm(rq, user);
        return new ResponseEntity(null,HttpStatus.OK);
    }

}
