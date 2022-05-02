package com.example.sendowl.domain.comment.dto;

import com.example.sendowl.domain.board.dto.BoardDto;
import com.example.sendowl.domain.board.entity.Board;
import com.example.sendowl.domain.comment.entity.Comment;
import com.example.sendowl.domain.user.dto.UserDto;
import com.example.sendowl.domain.user.repository.UserRepository;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public class CommentDto {

    private static UserRepository userRepository;

    @Data
    public static class CommentRes {
        private Long id;
        private BoardDto.BoardsRes board;
        private UserDto.UserRes user;
        private Comment parent;
        private String content;
        private LocalDateTime regDate;
        private Long depth;

        public CommentRes(Comment entity) {
            this.id = entity.getId();
            this.board = new BoardDto.BoardsRes((Page<Board>) entity.getBoard());
            this.user = new UserDto.UserRes(entity.getUser());
            this.parent = entity.getParent();
            this.content = entity.getContent();
            this.regDate = entity.getRegDate();
            this.depth = entity.getDepth();
        }
    }

    @Data
    @Getter
    @Builder
    public static class CommentReq {
        @NotNull
        private Long boardId;

        @NotNull
        private String email;

        private Long parentId;

        @NotNull
        private String content;

    }

    @Data
    @Getter
    @Builder
    public static class deleteReq {
        @NotNull
        private Long commentId;

    }

    @Data
    public static class DeleteRes {
        private Long id;

        public DeleteRes(Comment entity) {
            this.id = entity.getId();
        }
    }
}
