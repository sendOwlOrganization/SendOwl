package com.example.sendowl.domain.comment.dto;

import com.example.sendowl.domain.comment.entity.Comment;
import com.example.sendowl.domain.user.dto.UserDto;
import com.example.sendowl.domain.user.entity.User;
import com.sun.istack.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

public class CommentDto {

    //for nativeQuery
    public interface DtoInterface {
        Long getCommentId();

        String getContent();

        Long getDepth();

        Long getParentId();

        Long getUserId();

        String getName();

        String getNickName();

        String getMbti();

        Integer getAge();

        String getGender();

        String getProfileImage();

        Long getLikeCount();

        LocalDateTime getRegDate();

        LocalDateTime getModDate();


    }

    @Setter
    @Getter
    public static class CommentRes {
        private final Long id;
        private final String content;
        private final LocalDateTime regDate;
        private final LocalDateTime modDate;
        private final Long likeCount;
        private UserDto.UserPublicRes user;
        private List<CommentRes> children;

        public CommentRes(Comment entity) {
            this.id = entity.getId();
            this.user = new UserDto.UserPublicRes(entity.getUser());
            this.content = entity.getContent();
            this.regDate = entity.getRegDate();
            this.modDate = entity.getModDate();
            this.likeCount = entity.getLikeCount();
        }

        public CommentRes(DtoInterface dto) {
            this.id = dto.getCommentId();
            this.user = new UserDto.UserPublicRes(
                    User.builder()
                            .id(dto.getUserId())
                            .nickName(dto.getNickName())
                            .mbti(dto.getMbti())
                            .profileImage(dto.getProfileImage())
                            .build()
            );
            this.content = dto.getContent();
            this.regDate = dto.getRegDate();
            this.modDate = dto.getModDate();
            this.likeCount = dto.getLikeCount();
        }
    }

    @Data
    @Getter
    @Builder
    public static class CommentReq {
        @NotNull
        @Schema(description = "게시글 id", nullable = false, example = "1")
        private Long boardId;

        @Schema(description = "부모 댓글 id", nullable = true, example = "1")
        private Long parentId;

        @NotNull
        @Schema(description = "댓글 내용", nullable = false, example = "댓글 내용")
        private String content;

    }

    @Data
    @Getter
    @Builder
    public static class UpdateReq {
        @NotNull
        @Schema(description = "댓글 id", nullable = false, example = "1")
        private Long commentId;

        @NotNull
        @Schema(description = "수정할 댓글 내용", nullable = false, example = "댓글 내용")
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
