package com.example.sendowl.domain.balance.dto;

import com.example.sendowl.domain.balance.entity.Balance;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class BalanceDto {


    @Getter
    public static class BalanceRes {
        private Long balanceId;
        private String balanceTitle;
        private List<BalanceOptionRes> balanceOptionRes;

        public BalanceRes(Balance balance) {
            this.balanceId = balance.getId();
            this.balanceTitle = balance.getBalanceTitle();
            this.balanceOptionRes = balance.getBalanceOptionList().stream()
                    .map(balanceOption -> new BalanceOptionRes(
                            balanceOption.getId(),
                            balanceOption.getOptionTitle(),
                            balanceOption.getHit()))
                    .collect(Collectors.toList());
        }
    }

    @Getter
    @AllArgsConstructor
    public static class BalanceOptionRes {
        private Long id;
        private String optionTitle;
        private Long hit;
    }

    @Getter
    public static class GetAllBalanceRes {
        private List<BalanceRes> balances;

        public GetAllBalanceRes(List<Balance> items) {
            balances = items.stream().map(BalanceDto.BalanceRes::new).collect(Collectors.toList());
        }
    }

    @Getter
    public static class InsertBalanceOptionReq {
        private String title;
    }

    @Getter
    public static class InsertBalanceReq {
        private String balanceTitle;
        private List<InsertBalanceOptionReq> insertBalanceOptionReqs;
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
        private Long balanceOptionId;
    }
}