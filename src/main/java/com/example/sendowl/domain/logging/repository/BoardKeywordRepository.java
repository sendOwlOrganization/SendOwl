package com.example.sendowl.domain.logging.repository;

import com.example.sendowl.domain.logging.entity.BoardKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardKeywordRepository extends JpaRepository<BoardKeyword, Long> {
}
