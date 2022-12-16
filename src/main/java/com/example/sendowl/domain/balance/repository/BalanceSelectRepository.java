package com.example.sendowl.domain.balance.repository;

import com.example.sendowl.domain.balance.entity.Balance;
import com.example.sendowl.domain.balance.entity.BalanceSelect;
import com.example.sendowl.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface BalanceSelectRepository extends JpaRepository<BalanceSelect, Long>, JpaSpecificationExecutor {

    Optional<BalanceSelect> findByBalanceAndUser(Balance balance, User user);

}