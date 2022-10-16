package com.example.sendowl.domain.balance.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BalanceCount {
    private Long id;
    private String title;
    private String firstDetail;
    private String secondDetail;
    private Long firstCount;
    private Long secondCount;

    @Builder
    public BalanceCount(Long id, String title, String firstDetail, String secondDetail, Long firstCount, Long secondCount) {
        this.id = id;
        this.title = title;
        this.firstDetail = firstDetail;
        this.secondDetail = secondDetail;
        this.firstCount = firstCount;
        this.secondCount = secondCount;
    }

    @Override
    public String toString() {
        return "BalanceCount{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", firstDetail='" + firstDetail + '\'' +
                ", secondDetail='" + secondDetail + '\'' +
                ", firstCount=" + firstCount +
                ", secondCount=" + secondCount +
                '}';
    }
}