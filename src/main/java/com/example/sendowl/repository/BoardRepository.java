package com.example.sendowl.repository;


import com.example.sendowl.entity.board.Board;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByActive(boolean active);
}

