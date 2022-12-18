package com.example.sendowl.domain.comment.dto;

import com.example.sendowl.domain.comment.entity.Comment;
import com.example.sendowl.domain.user.dto.UserDto;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class CommentDto {

    @Getter
    public static class CommentRes {
        private final Long id;
        private final UserDto.UserPublicRes user;
        private final List<CommentRes> children;
        private final String content;
        private final LocalDateTime regDate;
        private final LocalDateTime modDate;
        private final Long commentLikeCount;

        public CommentRes(Comment entity) {
            this.id = entity.getId();
            this.user = new UserDto.UserPublicRes(entity.getUser());
            this.children = entity.getChildren().stream().map(CommentRes::new).collect(Collectors.toList());
            this.content = entity.getContent();
            this.regDate = entity.getRegDate();
            this.modDate = entity.getModDate();
            this.commentLikeCount = entity.getCommentLikeCount();
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
