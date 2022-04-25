package com.example.sendowl.domain.category.dto;

import com.example.sendowl.domain.category.entity.Category;
import com.example.sendowl.domain.category.entity.CategoryName;
import lombok.Data;

public class CategoryDto {

    @Data
    public static class CategoriesRes{
        private Long id;
        private CategoryName categoryName;

        public CategoriesRes(Category entity) {
            this.id = entity.getId();
            this.categoryName = entity.getCategoryName();
        }
    }
}
