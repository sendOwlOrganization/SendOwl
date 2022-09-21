package com.example.sendowl.domain.alarm.repository;

import com.example.sendowl.domain.alarm.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AlarmRepository extends JpaRepository<Alarm, Long>, JpaSpecificationExecutor {
}
