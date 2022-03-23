package com.example.sendowl.repository;


import com.example.sendowl.dto.BoardRequest;
import com.example.sendowl.entity.Board;
import com.example.sendowl.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByActive(int active);
}

