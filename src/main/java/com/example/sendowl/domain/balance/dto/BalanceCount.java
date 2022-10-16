package com.example.sendowl.domain.balance.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class BalanceCount {
    private Long id;
    private String title;
    @JsonProperty("a_detail") // json 직렬화시 문제가 발생하기 때문에 직접 key를 등록하여 문제를 해결한다.
    private String aDetail;
    @JsonProperty("b_detail") // json 직렬화시 문제가 발생하기 때문에 직접 key를 등록하여 문제를 해결한다.
    private String bDetail;
    @JsonProperty("a_count") // json 직렬화시 문제가 발생하기 때문에 직접 key를 등록하여 문제를 해결한다.
    private Long aCount;
    @JsonProperty("b_count") // json 직렬화시 문제가 발생하기 때문에 직접 key를 등록하여 문제를 해결한다.
    private Long bCount;

    public BalanceCount(Long id, String title, String aDetail, String bDetail, Long aCount, Long bCount) {
        this.id = id;
        this.title = title;
        this.aDetail = aDetail;
        this.bDetail = bDetail;
        this.aCount = aCount;
        this.bCount = bCount;
    }

    @Override
    public String toString() {
        return "BalanceCount{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", aDetail='" + aDetail + '\'' +
                ", bDetail='" + bDetail + '\'' +
                ", aCount=" + aCount +
                ", bCount=" + bCount +
                '}';
    }
}