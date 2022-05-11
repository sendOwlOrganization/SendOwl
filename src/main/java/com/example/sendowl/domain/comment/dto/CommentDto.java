package com.example.sendowl.domain.comment.dto;

import com.example.sendowl.domain.comment.entity.Comment;
import com.example.sendowl.domain.user.dto.UserDto;
import com.example.sendowl.domain.user.repository.UserRepository;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

public class CommentDto {

    private static UserRepository userRepository;

    @Data
    public static class CommentRes {
        private Long id;
        private Long boardId;
        private UserDto.UserRes user;
        private Comment parent;
        private String content;
        private LocalDateTime regDate;
        private LocalDateTime modDate;
        private Long depth;

        public CommentRes(Comment entity) {
            this.id = entity.getId();
            this.boardId = entity.getBoard().getId();
            this.user = new UserDto.UserRes(entity.getUser());
            this.parent = entity.getParent();
            this.content = entity.getContent();
            this.regDate = entity.getRegDate();
            this.modDate = entity.getModDate();
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
    public static class UpdateReq {
        @NotNull
        private Long commentId;

        @NotNull
        private String content;

    }

    @Data
    @Getter
    @Builder
    public static class DeleteReq {
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
