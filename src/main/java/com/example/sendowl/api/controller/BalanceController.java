package com.example.sendowl.api.controller;

import com.example.sendowl.api.service.BalanceService;
import com.example.sendowl.auth.PrincipalDetails;
import com.example.sendowl.domain.balance.dto.BalanceDto;
import com.example.sendowl.domain.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/balances")
public class BalanceController {

    private final BalanceService balanceService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "밸런스게임 등록하기", description = "밸런스 게임을 등록한다.", security = { @SecurityRequirement(name = "bearerAuth") })
    @PostMapping // 밸런스 게임 등록
    void insertBalance(final @Valid @RequestBody BalanceDto.InsertBalanceReq rq){
        balanceService.insertBalance(rq);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "밸런스게임 수정하기", description = "밸런스 게임을 수정한다.", security = { @SecurityRequirement(name = "bearerAuth") })
    @PutMapping // 밸런스 게임 등록
    void updateBalance(final @Valid @RequestBody BalanceDto.UpdateBalanceReq rq) {
        balanceService.updateBalance(rq);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "밸런스게임 삭제하기", description = "밸런스 게임을 소프트 삭제한다.", security = { @SecurityRequirement(name = "bearerAuth") })
    @DeleteMapping("/{id}") // 밸런스 게임 등록
    void updateBalance(final @PathVariable Long id){
        balanceService.deleteBalance(id);
    }
    @Operation(summary = "밸런스게임 최신순 10개 조회", description = "밸런스 게임 10개를 받아온다.")
    @GetMapping  // 밸런스 게임 등록
    public ResponseEntity<?> getAllBalance(){
        return new ResponseEntity<>(balanceService.getAllBalances(), HttpStatus.OK);
    }
    @Operation(summary = "밸런스 게임 자세히 조회", description = "하나의 밸런스 게임의 데이터를 자세히 받아온다.")
    @GetMapping("/{balanceId}")  // 밸런스 게임 등록
    public ResponseEntity<?> getDetailBalance(final @PathVariable Long balanceId){
        // 밸런스를 토대로 해당 유저들의 데이터를 가져와야한다.
        return new ResponseEntity<>(balanceService.getBalances(balanceId), HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "밸런스 게임 투표하기", description = "밸런스 게임에 투표한다.", security = { @SecurityRequirement(name = "bearerAuth") })
    @PostMapping("/vote")  // 밸런스 게임 등록
    public void voteBalanceGame(final @Valid @RequestBody BalanceDto.VoteBalanceReq rq){
        PrincipalDetails principal = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = principal.getUser();
        balanceService.voteBalanceGame(rq, user);
    }
}
