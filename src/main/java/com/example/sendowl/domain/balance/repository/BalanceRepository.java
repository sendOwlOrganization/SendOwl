package com.example.sendowl.domain.balance.repository;

import com.example.sendowl.domain.balance.entity.Balance;
import com.example.sendowl.domain.balance.entity.BalanceCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface BalanceRepository  extends JpaRepository<Balance, Long>, JpaSpecificationExecutor {

    List<Balance> findTop10ByIsDeletedFalseOrderByRegDateDesc();
}
