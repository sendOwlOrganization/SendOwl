package com.example.sendowl.domain.like.repository;

import com.example.sendowl.domain.comment.entity.Comment;
import com.example.sendowl.domain.like.entity.CommentLike;
import com.example.sendowl.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long>, JpaSpecificationExecutor {

    Optional<CommentLike> findByUserAndComment(User user, Comment comment);
}
