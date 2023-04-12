package com.example.sendowl.domain.category.dto;

import lombok.Getter;

@Getter
public class CategoryCount {
    private final Long categoryId;
    private final String name;
    private final Long count;

    public CategoryCount(Long categoryId, String name, Long count) {
        this.categoryId = categoryId;
        this.name = name;
        this.count = count;
    }
}
