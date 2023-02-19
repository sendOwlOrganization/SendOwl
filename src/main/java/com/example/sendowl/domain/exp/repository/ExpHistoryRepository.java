package com.example.sendowl.domain.exp.repository;

import com.example.sendowl.domain.exp.entity.ExpHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ExpHistoryRepository extends JpaRepository<ExpHistory, Long>, JpaSpecificationExecutor {
}
