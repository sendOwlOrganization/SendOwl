package com.example.sendowl.domain.comment.repository;


import com.example.sendowl.domain.board.entity.Board;
import com.example.sendowl.domain.comment.dto.CommentDto;
import com.example.sendowl.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "SELECT c FROM Comment c JOIN FETCH c.user WHERE c.board=:board and c.depth=0")
    Optional<List<Comment>> findAllByBoard(Board board);

    @Query(value = "SELECT comment_id as commentId, content, depth, parent_id as parentId  FROM " +
            "(SELECT *, RANK() OVER (PARTITION BY c.parent_id ORDER BY c.reg_date desc) as a " +
            "FROM comment c where c.depth = 1 AND c.parent_id in :commentList) as rankrow WHERE rankrow.a <= 5",
    nativeQuery = true)
    List<CommentDto.SimpleCommentDto> findChildComment(List<Long> commentList);
}

