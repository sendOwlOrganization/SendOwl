package com.example.sendowl.domain.like.dto;

import com.example.sendowl.domain.board.entity.Board;
import com.example.sendowl.domain.comment.entity.Comment;
import com.example.sendowl.domain.like.entity.BoardLike;
import com.example.sendowl.domain.like.entity.CommentLike;
import com.example.sendowl.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class LikeDto {
    @Getter
    @NoArgsConstructor
    static public class BoardLikeRequest {
        private Long boardId;

        public BoardLikeRequest(Long boardId) {
            this.boardId = boardId;
        }

        public BoardLike toEntity(User user, Board board) {
            return BoardLike.builder()
                    .board(board)
                    .user(user)
                    .build();
        }
    }

    @Getter
    static public class BoardLikeResponse {
        private final Long id;
        private final Long userId;
        private final Long boardId;

        public BoardLikeResponse(BoardLike boardLike) {
            this.id = boardLike.getId();
            this.userId = boardLike.getUser().getId();
            this.boardId = boardLike.getBoard().getId();
        }
    }

    @Getter
    static public class BoardUnLikeRequest {
        private Long boardId;
    }

    @Getter
    @NoArgsConstructor
    static public class CommentLikeRequest {
        private Long commentId;

        public CommentLikeRequest(Long commentId) {
            this.commentId = commentId;
        }

        public CommentLike toEntity(User user, Comment comment) {
            return CommentLike.builder()
                    .comment(comment)
                    .user(user)
                    .build();
        }
    }

    @Getter
    static public class CommentLikeResponse {
        private final Long id;
        private final Long userId;
        private final Long commentId;

        public CommentLikeResponse(CommentLike commentLike) {
            this.id = commentLike.getId();
            this.userId = commentLike.getUser().getId();
            this.commentId = commentLike.getComment().getId();
        }
    }

    @Getter
    static public class CommentUnLikeRequest {
        private Long commentId;
    }

}
