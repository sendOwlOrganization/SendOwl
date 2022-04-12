package com.example.sendowl.repository;


import com.example.sendowl.entity.board.Board;
import com.example.sendowl.entity.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findTopByParentIdOrderByOrdDesc(Long id);

    @Query(value = "SELECT c FROM Comment c JOIN FETCH c.board JOIN FETCH c.member WHERE c.board=:board")
    Optional<List<Comment>> findAllByBoard(Board board);
}

