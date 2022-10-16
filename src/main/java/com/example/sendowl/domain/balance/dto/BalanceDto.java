package com.example.sendowl.domain.balance.dto;

import com.example.sendowl.domain.balance.entity.Balance;
<<<<<<< HEAD
=======
import com.fasterxml.jackson.annotation.JsonProperty;
>>>>>>> d16aa5d (feat: BalanceDto)
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class BalanceDto {

    @Getter
    public static class BalanceRes {
        private Long id;
        private String title;
        private String firstDetail;
        private String secondDetail;
        private Long firstCount;
        private Long secondCount;


        public BalanceRes(BalanceCount balance) {
            this.id = balance.getId();
            this.title = balance.getTitle();
            this.firstDetail = balance.getFirstDetail();
            this.secondDetail = balance.getSecondDetail();
            this.firstCount = balance.getFirstCount();
            this.secondCount = balance.getSecondCount();
        }
    }
    @Getter
    public static class GetAllBalanceRes {
        private List<BalanceRes> balances;
        public GetAllBalanceRes(List<BalanceCount> items) {
            balances = items.stream().map(BalanceDto.BalanceRes::new).collect(Collectors.toList());
        }
    }

    @Getter
    public static class InsertBalanceReq {
        private String title;
        private String firstDetail;
        private String secondDetail;


        public Balance toEntity(){
            return Balance.builder()
                    .title(this.title)
                    .firstDetail(this.firstDetail)
                    .secondDetail(this.secondDetail)
                    .build();
        }
    }

    @Getter
    public static class UpdateBalanceReq {
        private Long id;
        private String title;
        private String firstDetail;
        private String secondDetail;
    }
    @Getter
    public static class VoteBalanceReq {
        private Long balanceId;
        private String pick;
    }
}