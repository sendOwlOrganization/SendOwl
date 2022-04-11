package com.example.sendowl.repository;


import com.example.sendowl.entity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Override
    Optional<User> findById(Long aLong);

    Optional<User> findByMemEmail(String memEmail); // Optional의 값은 T value에 저장되어있어서 참조하더라도 바로 NPE이 뜨지 않는다.

    Optional<User> findByMemId(String memId);
}

