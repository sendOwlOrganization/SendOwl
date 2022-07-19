package com.example.sendowl.domain.user.repository;


import com.example.sendowl.domain.user.dto.UserMbti;
import com.example.sendowl.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsUserByEmail(String email);
    boolean existsUserByEmailAndTransactionId(String email, String trancationId);

    @Query(value = "select new com.example.sendowl.domain.user.dto.UserMbti(u.mbti, count(u)) " +
            "from User u group by u.mbti")
    List<UserMbti> findAllWithJPQL();

    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndTransactionId(String email, String trancationId);
}

