package com.example.sendowl.domain.board.dto;

import com.example.sendowl.domain.board.entity.Board;
import com.example.sendowl.domain.category.entity.Category;
import com.example.sendowl.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Optional;


public class BoardDto {

    @Data
    public static class BoardsRes {
        private Long id;
        private User user;
        private String title;
        private String content;
        private LocalDateTime regDate;
        private Integer hit;

        public BoardsRes(Board entity) {
            this.id = entity.getId();
            this.user = entity.getUser();
            this.title = entity.getTitle();
            this.content = entity.getContent();
            this.regDate = entity.getRegDate();
            this.hit = entity.getHit();
        }
    }

    @Data
    public static class BoardReq {
        @NotBlank
        private String title;
        @NotBlank
        private String content;
        @NotBlank
        private String email;
        @NotBlank
        private String categoryName;

        public BoardReq(Board entity) {
            this.title = entity.getTitle();
            this.content = entity.getContent();
            this.email = entity.getUser().getEmail();
            this.categoryName = String.valueOf(entity.getCategory().getCategoryName());
        }
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class DetailRes {
        private Long id;
        private String title;
        private String content;
        private String email;
        private Integer hit;

        public DetailRes(Board entity) {
            this.id = entity.getId();
            this.title = entity.getTitle();
            this.email = entity.getUser().getEmail();
            this.content = entity.getContent();
            this.hit = entity.getHit();
        }
    }


}
