package com.example.sendowl.domain.balance.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BalanceCount {
    private final Long id;
    private final String title;
    private final String firstDetail;
    private final String secondDetail;
    private final Long firstCount;
    private final Long secondCount;

    @Builder
    public BalanceCount(Long id, String title, String firstDetail, String secondDetail, Long firstCount, Long secondCount) {
        this.id = id;
        this.title = title;
        this.firstDetail = firstDetail;
        this.secondDetail = secondDetail;
        this.firstCount = firstCount;
        this.secondCount = secondCount;
    }
}