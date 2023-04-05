package com.example.sendowl.domain.category.dto;

import com.example.sendowl.domain.category.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CategoryDto {

    @Getter
    @NoArgsConstructor
    public static class CategoriesRes {
        private Long id;
        private String name;

        public CategoriesRes(Category entity) {
            this.id = entity.getId();
            this.name = entity.getName();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class CategoriesCountRes {
        private Long id;
        private String name;
        private Long count;

        public CategoriesCountRes(CategoryCount dto) {
            this.id = dto.getCategoryId();
            this.name = dto.getName();
            this.count = dto.getCount();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class CategoryInsertReq {
        @NotBlank
        @Schema(description = "카테고리 이름", nullable = false, example = "기타")
        private String name;

        public Category toEntity() {
            return new Category(name);
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoryUpdateReq {
        @NotNull
        @Schema(description = "카테고리 id", nullable = false, example = "1")
        private Long id;
        @NotBlank
        @Schema(description = "카테고리 이름", nullable = false, example = "기타")
        private String name;

        public Category toEntity() {
            return new Category(name);
        }
    }

    @Getter
    @NoArgsConstructor
    public static class CategoryDeleteReq {
        @NotNull
        private Long id;
    }
}
