package com.example.sendowl.domain.balance.dto;

import com.example.sendowl.domain.balance.entity.Balance;
import io.swagger.v3.oas.annotations.media.Schema;
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
        private final List<BalanceRes> balances;

        public GetAllBalanceRes(List<Balance> items) {
            balances = items.stream().map(BalanceDto.BalanceRes::new).collect(Collectors.toList());
        }
    }

    @Getter
    public static class InsertBalanceOptionReq {
        @Schema(description = "밸런스 게임 선택 항목 제목", nullable = false, example = "깻잎을 떼준다고?!?!")
        private String title;
    }

    @Getter
    public static class InsertBalanceReq {
        @Schema(description = "밸런스 게임 제목", nullable = false, example = "깻잎 논쟁")
        private String balanceTitle;
        private List<InsertBalanceOptionReq> insertBalanceOptionReqs;
    }
    
    @Getter
    public static class VoteBalanceReq {
        @Schema(description = "밸런스 게임 id", nullable = false, example = "1")
        private Long balanceId;
        @Schema(description = "밸런스 게임 선택 항목 id", nullable = false, example = "1")
        private Long balanceOptionId;
    }
}