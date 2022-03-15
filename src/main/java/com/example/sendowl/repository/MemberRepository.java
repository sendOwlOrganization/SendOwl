package com.example.sendowl.repository;


import com.example.sendowl.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Override
    Optional<Member> findById(Long aLong);

    Optional<Member> findByMemEmail(String memEmail); // Optional의 값은 T value에 저장되어있어서 참조하더라도 바로 NPE이 뜨지 않는다.
}

