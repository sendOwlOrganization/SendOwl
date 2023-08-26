package com.example.sendowl.domain.tag.dto;

import lombok.Getter;

@Getter
public class TagCount {
    private final Long tagId;
    private final String name;
    private final Long count;

    public TagCount(Long tagId, String name, Long count) {
        this.tagId = tagId;
        this.name = name;
        this.count = count;
    }
}
