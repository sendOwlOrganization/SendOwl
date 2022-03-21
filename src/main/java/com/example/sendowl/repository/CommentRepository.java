package com.example.sendowl.repository;


import com.example.sendowl.entity.Board;
import com.example.sendowl.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}

