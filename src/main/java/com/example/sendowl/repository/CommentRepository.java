package com.example.sendowl.repository;


import com.example.sendowl.entity.Board;
import com.example.sendowl.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findTopByParentIdOrderByOrdDesc(Long id);
}

