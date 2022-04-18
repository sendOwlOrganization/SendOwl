package com.example.sendowl.domain.board.repository;


import com.example.sendowl.domain.board.dto.BoardDto;
import com.example.sendowl.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import static com.example.sendowl.domain.board.dto.BoardDto.*;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<BoardRes> findByActive(boolean active);
}

