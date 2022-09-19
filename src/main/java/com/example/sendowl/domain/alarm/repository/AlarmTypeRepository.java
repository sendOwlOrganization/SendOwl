package com.example.sendowl.domain.alarm.repository;

import com.example.sendowl.domain.alarm.entity.Alarm;
import com.example.sendowl.domain.alarm.entity.AlarmType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AlarmTypeRepository extends JpaRepository<AlarmType, Long>, JpaSpecificationExecutor {
}
