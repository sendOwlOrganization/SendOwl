package com.example.sendowl.repository;


import com.example.sendowl.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Override
    Optional<Member> findById(Long aLong);

    Optional<Member> findByMemEmail(String memEmail);
}

