package com.example.sendowl.domain.balance.repository;

import com.example.sendowl.domain.balance.entity.Balance;
<<<<<<< HEAD
import com.example.sendowl.domain.balance.entity.BalanceCheck;
import com.example.sendowl.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface BalanceCheckRepository extends JpaRepository<BalanceCheck, Long>, JpaSpecificationExecutor {

    Optional<BalanceCheck> findByBalanceAndUser(Balance balance, User user);
=======
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BalanceRepository  extends JpaRepository<Balance, Long>, JpaSpecificationExecutor {
    
>>>>>>> 799c1e2 (feat: BalanceController)
}
