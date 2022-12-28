package com.example.sendowl.domain.logging.repository;

import com.example.sendowl.domain.logging.entity.SearchLogs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchLogsRepository extends JpaRepository<SearchLogs, Long> {

}
