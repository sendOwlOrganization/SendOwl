package com.example.sendowl.repository;


import com.example.sendowl.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByActive(Long active);
}

