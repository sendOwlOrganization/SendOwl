package com.example.sendowl.domain.balance.dto;

import com.example.sendowl.domain.balance.entity.Balance;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class BalanceDto {

    public static class BalanceRes {
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

        public Long getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getaDetail() {
            return aDetail;
        }

        public String getbDetail() {
            return bDetail;
        }

        public Long getaCount() {
            return aCount;
        }

        public Long getbCount() {
            return bCount;
        }

        public BalanceRes(BalanceCount balance) {
            this.id = balance.getId();
            this.title = balance.getTitle();
            this.aDetail = balance.getADetail();
            this.bDetail = balance.getBDetail();
            this.aCount = balance.getACount();
            this.bCount = balance.getBCount();
        }
    }
    @Getter
    public static class GetAllBalanceRes {
        private List<BalanceRes> balances;
        public GetAllBalanceRes(List<BalanceCount> items) {
            balances = items.stream().map(BalanceDto.BalanceRes::new).collect(Collectors.toList());
        }
    }

    public static class InsertBalanceReq {
        private String title;
        @JsonProperty("a_detail") // json 직렬화시 문제가 발생하기 때문에 직접 key를 등록하여 문제를 해결한다.
        private String aDetail;
        @JsonProperty("b_detail") // json 직렬화시 문제가 발생하기 때문에 직접 key를 등록하여 문제를 해결한다.
        private String bDetail;

        public String getTitle() {
            return title;
        }

        public String getaDetail() {
            return aDetail;
        }

        public String getbDetail() {
            return bDetail;
        }

        public Balance toEntity(){
            return Balance.builder()
                    .title(this.title)
                    .aDetail(this.aDetail)
                    .bDetail(this.bDetail)
                    .build();
        }
    }

    public static class UpdateBalanceReq {
        private Long id;
        private String title;
        @JsonProperty("a_detail") // json 직렬화시 문제가 발생하기 때문에 직접 key를 등록하여 문제를 해결한다.
        private String aDetail;
        @JsonProperty("b_detail") // json 직렬화시 문제가 발생하기 때문에 직접 key를 등록하여 문제를 해결한다.
        private String bDetail;

        public Long getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getaDetail() {
            return aDetail;
        }

        public String getbDetail() {
            return bDetail;
        }
    }
    @Getter
    public static class VoteBalanceReq {
        private Long balanceId;
        private String pick;
    }
}