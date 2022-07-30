package com.example.sendowl.domain.category.dto;

import lombok.Getter;

@Getter
public class CategoryCount {
    private Long categoryId;
    private String name;
    private Long count;

    public CategoryCount(Long categoryId, String name, Long count) {
        this.categoryId = categoryId;
        this.name = name;
        this.count = count;
    }

    @Override
    public String toString() {
        return "CategoryCount{" +
                "categoryId=" + categoryId +
                ", name='" + name + '\'' +
                ", count=" + count +
                '}';
    }
}
