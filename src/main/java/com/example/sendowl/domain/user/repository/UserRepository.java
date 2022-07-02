package com.example.sendowl.domain.user.repository;


import com.example.sendowl.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsUserByEmail(String email);
    boolean existsUserByEmailAndTransactionId(String email, String trancationId);

    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndTransactionId(String email, String trancationId);
}

