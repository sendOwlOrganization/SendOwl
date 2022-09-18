package com.example.sendowl.domain.alarm.repository;

import com.example.sendowl.domain.alarm.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long>  {
}
