package com.example.sendowl.domain.category.dto;

import com.example.sendowl.domain.category.entity.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CategoryDto {

    @Getter
    @NoArgsConstructor
    public static class CategoriesRes{
        private Long id;
        private String categoryName;

        public CategoriesRes(Category entity) {
            this.id = entity.getId();
            this.categoryName = entity.getCategoryName();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class CategoryInsertReq {
        @NotBlank
        private String categoryName;

        public Category toEntity() {
            return new Category(categoryName);
        }
    }

    @Getter
    @NoArgsConstructor
    public static class CategoryUpdateReq {
        @NotNull
        private Long id;
        @NotBlank
        private String categoryName;

        public Category toEntity() {
            return new Category(categoryName);
        }
    }
    @Getter
    @NoArgsConstructor
    public static class CategoryDeleteReq {
        @NotNull
        private Long id;
    }
}
