package com.example.sendowl.api.controller;

import com.example.sendowl.api.service.BalanceService;
import com.example.sendowl.auth.PrincipalDetails;
import com.example.sendowl.config.SecurityConfigure;
import com.example.sendowl.domain.balance.dto.BalanceDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/balances")
public class BalanceController {

    private final BalanceService balanceService;

    @Operation(summary = "밸런스게임 등록하기", description = "밸런스 게임을 등록한다.")
    @PostMapping // 밸런스 게임 등록
    void insertBalance(final @Valid @RequestBody BalanceDto.InsertBalanceReq rq){
        balanceService.insertBalance(rq);
    }

    @Operation(summary = "밸런스게임 수정하기", description = "밸런스 게임을 수정한다.")
    @PutMapping // 밸런스 게임 등록
    void updateBalance(final @Valid @RequestBody BalanceDto.UpdateBalanceReq rq) {
        balanceService.updateBalance(rq);
    }

    @Operation(summary = "밸런스게임 삭제하기", description = "밸런스 게임을 소프트 삭제한다.")
    @DeleteMapping("/{id}") // 밸런스 게임 등록
    void updateBalance(final @PathVariable Long id){
        balanceService.deleteBalance(id);
    }

    @Operation(summary = "밸런스게임 최신순 10개 가져오기", description = "밸런스 게임 10개를 받아온다.")
    @GetMapping  // 밸런스 게임 등록
    public ResponseEntity<?> getAllBalance(){
        return new ResponseEntity<>(balanceService.getBalances(), HttpStatus.OK);
    }

    @Operation(summary = "밸런스 게임 투표하기", description = "밸런스 게임에 투표한다.")
    @PostMapping("/vote")  // 밸런스 게임 등록
    public ResponseEntity<?> voteBalanceGame(final @Valid @RequestBody BalanceDto.VoteBalanceReq rq){
        // 사용자 토큰 확인하기
        PrincipalDetails principal = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        balanceService.voteBalanceGame(rq, principal.getUser());
        // 사용자 토큰 기반으로 어디에 투표하는지
        return new ResponseEntity<>(balanceService.getBalances(), HttpStatus.OK);
    }


}
