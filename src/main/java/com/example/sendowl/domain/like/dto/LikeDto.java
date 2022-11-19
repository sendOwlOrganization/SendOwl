package com.example.sendowl.domain.like.dto;

import com.example.sendowl.domain.board.entity.Board;
import com.example.sendowl.domain.like.entity.BoardLike;
import com.example.sendowl.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class LikeDto {
    @Getter
    @NoArgsConstructor
    static public class BoardLikeRequest {
        private Long boardId;

        public BoardLike toEntity(User user, Board board) {
            return BoardLike.builder()
                    .board(board)
                    .user(user)
                    .build();
        }

        public BoardLikeRequest(Long boardId) {
            this.boardId = boardId;
        }
    }

    @Getter
    static public class BoardLikeResponse {
        private Long id;
        private Long userId;
        private Long boardId;
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
}
