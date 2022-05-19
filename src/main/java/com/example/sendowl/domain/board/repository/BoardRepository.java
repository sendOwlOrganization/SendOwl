package com.example.sendowl.domain.board.repository;


import com.example.sendowl.domain.board.entity.Board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, JpaSpecificationExecutor {

    List<Board> findByIsDeleted(boolean isDeleted);

    @Query(value = "SELECT b FROM Board b join fetch b.user",
            countQuery = "SELECT COUNT(b) FROM Board b")
    Page<Board> findAllFetchJoin(Pageable pageable);

    @Query(value = "SELECT b FROM Board b join fetch b.user where b.category.id = :categoryId",
            countQuery = "SELECT COUNT(b) FROM Board b where b.category.id = :categoryId")
    Page<Board> findByCategoryIdFetchJoin(Long categoryId, Pageable pageable);

    Page<Board> findByTitleContaining(Pageable pageable, String text);
    Page<Board> findByContentContaining(Pageable pageable, String text);

}

