package com.example.sendowl.domain.balance.repository;

import com.example.sendowl.domain.balance.entity.BalanceOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BalanceOptionRepository extends JpaRepository<BalanceOption, Long>, JpaSpecificationExecutor {

}