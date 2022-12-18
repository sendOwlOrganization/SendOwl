package com.example.sendowl.domain.blame.entity;

public enum BlameContentType {
    BOARD("BOARD", 1L),
    COMMENT("COMMENT", 2L);

    private final String title;
    private final Long index;

    BlameContentType(String title, Long index) {
        this.title = title;
        this.index = index;
    }
}
