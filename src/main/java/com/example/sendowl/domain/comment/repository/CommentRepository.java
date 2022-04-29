package com.example.sendowl.domain.comment.repository;


import com.example.sendowl.domain.board.entity.Board;
import com.example.sendowl.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "SELECT c FROM Comment c JOIN FETCH c.board JOIN FETCH c.user WHERE c.board=:board")
    Optional<List<Comment>> findAllByBoard(Board board);

    // WHERE c.active = true
    @Query(value = "SELECT Count(c) FROM Comment c WHERE c.parent.id=:commentId AND c.active = 'Y'")
    Long findChildrenById(Long commentId);
}

