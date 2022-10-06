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

    @Getter
    public static class CommentRes {
        private Long id;
        private Long boardId;
        private UserDto.UserPublicRes user;
        private Long parent; // 이게 프론트 입장에서 필요한지 잘 모르겠음...child는 있으면 좋을듯
        private String content;
        private LocalDateTime regDate;
        private LocalDateTime modDate;
        private Long depth;

        public CommentRes(Comment entity) {
            this.id = entity.getId();
            this.boardId = entity.getBoard().getId();
            this.user = new UserDto.UserPublicRes(entity.getUser());
            this.parent = entity.getParent()!=null?entity.getParent().getId():null;
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
