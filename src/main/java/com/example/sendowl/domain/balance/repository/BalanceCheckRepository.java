package com.example.sendowl.domain.balance.repository;

import com.example.sendowl.domain.balance.entity.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BalanceRepository  extends JpaRepository<Balance, Long>, JpaSpecificationExecutor {
    
}
