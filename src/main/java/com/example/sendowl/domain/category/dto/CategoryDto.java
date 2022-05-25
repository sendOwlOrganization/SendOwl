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
        private String name;
        private String koName;

        public CategoriesRes(Category entity) {
            this.id = entity.getId();
            this.name = entity.getName();
            this.koName = entity.getKoName();
        }
    }
    @Getter
    @NoArgsConstructor
    public static class CategoriesCountRes{
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
        private String name;
        public Category toEntity() {
            return new Category(name);
        }
    }

    @Getter
    @NoArgsConstructor
    public static class CategoryUpdateReq {
        @NotNull
        private Long id;
        @NotBlank
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
