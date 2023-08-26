package com.example.sendowl.domain.user.repository;


import com.example.sendowl.domain.tag.entity.Tag;
import com.example.sendowl.domain.user.dto.UserMbti;
import com.example.sendowl.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsUserByNickName(String nickName);

    boolean existsUserByEmailAndTransactionId(String email, String trancationId);

    Optional<User> findUserByEmailAndTransactionId(String email, String trancationId);

    @Query(value = "select new com.example.sendowl.domain.user.dto.UserMbti(u.mbti, count(u)) " +
            "from User u group by u.mbti")
    List<UserMbti> findAllUserMbtiWithCount();

    @Query(value = "select new com.example.sendowl.domain.user.dto.UserMbti(U.mbti, count(U))" +
            "from User U where U in (select U1 from Board B join User U1 on B.user = U1 ) group by U.mbti order by count(U) desc")
    List<UserMbti> findUserMbtiFromCategory(Tag tag);

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndTransactionId(String email, String trancationId);

    Optional<User> findUserByIdAndModDateBetween(Long userId, LocalDateTime today, LocalDateTime tomorrow);
}

