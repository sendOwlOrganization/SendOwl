package com.example.sendowl.domain.comment.dto;

import com.example.sendowl.domain.comment.entity.Comment;
import com.example.sendowl.domain.user.dto.UserDto;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

public class CommentDto {

    @Setter
    @Getter
    public static class CommentRes {
        private final Long id;
        private UserDto.UserPublicRes user;
        private List<CommentRes> children;
        private final String content;
        private final LocalDateTime regDate;
        private final LocalDateTime modDate;
        private final Long commentLikeCount;

        public CommentRes(Comment entity) {
            this.id = entity.getId();
            this.user = new UserDto.UserPublicRes(entity.getUser());
            this.content = entity.getContent();
            this.regDate = entity.getRegDate();
            this.modDate = entity.getModDate();
            this.commentLikeCount = entity.getCommentLikeCount();
        }

        public CommentRes(SimpleCommentDto dto) {
            this.id = dto.getCommentId();
            //this.user = new UserDto.UserPublicRes(entity.getUser());
            this.content = dto.getContent();
            //this.regDate = dto.getRegDate();
            //this.modDate = entity.getModDate();
            //this.commentLikeCount = entity.getCommentLikeCount();

            //tmpdata
            this.regDate = LocalDateTime.of(2022, Month.JANUARY, 1, 12, 30, 40);;
            this.modDate = LocalDateTime.of(2022, Month.JANUARY, 1, 12, 30, 40);;
            this.commentLikeCount = 2L;
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


    //for nativeQuery
    public interface SimpleCommentDto {
        Long getCommentId();

        String getContent();

        Long getDepth();

        Long getParentId();

    }


}
