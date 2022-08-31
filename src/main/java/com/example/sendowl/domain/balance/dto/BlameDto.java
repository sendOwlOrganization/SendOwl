package com.example.sendowl.domain.balance.dto;

import com.example.sendowl.domain.balance.entity.Balance;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
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

        public BalanceRes(Balance balance) {
            this.id = balance.getId();
            this.title = balance.getTitle();
            this.aDetail = balance.getaDetail();
            this.bDetail = balance.getbDetail();
        }

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
    public static class GetBalanceRes {
        private List<BalanceRes> balances;
        public GetBalanceRes(List<Balance> items) {
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

        @Override
        public String toString() {
            return "BalanceReq{" +
                    "title='" + title + '\'' +
                    ", aDetail='" + aDetail + '\'' +
                    ", bDetail='" + bDetail + '\'' +
                    '}';
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

        @Override
        public String toString() {
            return "BalanceReq{" +
                    "title='" + title + '\'' +
                    ", aDetail='" + aDetail + '\'' +
                    ", bDetail='" + bDetail + '\'' +
                    '}';
        }
    }

    @Getter
    public static class VoteBalanceReq {
        private Long balanceId;
        private String pick;
    }
}