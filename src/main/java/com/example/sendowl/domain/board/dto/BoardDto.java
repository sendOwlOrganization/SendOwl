package com.example.sendowl.domain.board.dto;

import com.example.sendowl.domain.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;


public class BoardDto {

    @Data
    @AllArgsConstructor
    @Builder
    public static class BoardRes {
        private Long id;
        private String title;
        private String content;
        private String regName;
        private String categoryName;
        private Integer hit;

        public BoardRes(Board entity) {
            // 수정 필요
            this.id = entity.getId();
            this.title = entity.getTitle();
            this.content = entity.getContent();
            this.regName = entity.getUser().getName();
            this.categoryName = entity.getCategory().getCategoryName();
            this.hit = entity.getHit();
        }
    }

    @Data
    public static class BoardReq {
        @NotBlank
        private Long id;
        @NotBlank
        private String title;
        @NotBlank
        private String content;
        @NotBlank
        private Long regId;
        @NotBlank
        private Long categoryId;
    }
}
