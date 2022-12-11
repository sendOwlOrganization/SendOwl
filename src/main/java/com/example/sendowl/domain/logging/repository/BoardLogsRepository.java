package com.example.sendowl.domain.logging.repository;

import com.example.sendowl.domain.logging.entity.BoardLogs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardLogsRepository extends JpaRepository<BoardLogs, Long> {
}
