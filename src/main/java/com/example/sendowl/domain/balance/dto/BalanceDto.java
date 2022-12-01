package com.example.sendowl.domain.balance.dto;

import com.example.sendowl.domain.balance.entity.Balance;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class BalanceDto {

    @Getter
    public static class BalanceRes {
        private Long balanceId;
        private String balanceTitle;
        private String firstDetail;
        private String secondDetail;
        private Long firstCount;
        private Long secondCount;

        public BalanceRes(BalanceCount balance) {
            this.balanceId = balance.getId();
            this.balanceTitle = balance.getTitle();
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
        private String balanceTitle;
        private String firstDetail;
        private String secondDetail;

        public Balance toEntity() {
            return Balance.builder()
                    .balanceTitle(this.balanceTitle)
                    .firstDetail(this.firstDetail)
                    .secondDetail(this.secondDetail)
                    .build();
        }
    }

    @Getter
    public static class UpdateBalanceReq {
        private Long balanceId;
        private String balanceTitle;
        private String firstDetail;
        private String secondDetail;
    }

    @Getter
    public static class VoteBalanceReq {
        private Long balanceId;
        private String pick;
    }
}