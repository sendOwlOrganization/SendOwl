package com.example.sendowl.api.controller;

import com.example.sendowl.api.service.BalanceService;
import com.example.sendowl.domain.balance.dto.BalanceDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/balances")
public class BalanceController {

    private final BalanceService balanceService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "[admin] 밸런스게임 등록", description = "밸런스 게임을 등록한다.", security = {@SecurityRequirement(name = "bearerAuth")})
    @PostMapping
    public ResponseEntity<?> insertBalance(final @Valid @RequestBody BalanceDto.InsertBalanceReq rq) {
        return new ResponseEntity<>(balanceService.insertBalance(rq), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "[admin] 밸런스게임 삭제", description = "밸런스 게임을 소프트 삭제한다.", security = {@SecurityRequirement(name = "bearerAuth")})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> updateBalance(final @PathVariable Long id) {
        return new ResponseEntity<>(balanceService.deleteBalance(id), HttpStatus.OK);
    }


    @Operation(summary = "밸런스게임 최신순 10개 조회", description = "밸런스 게임 10개를 받아온다.")
    @GetMapping
    public ResponseEntity<BalanceDto.GetAllBalanceRes> getAllBalance() {
        return new ResponseEntity<>(balanceService.getAllBalances(), HttpStatus.OK);
    }

    @Operation(summary = "밸런스 게임 자세히 조회", description = "하나의 밸런스 게임의 데이터를 자세히 받아온다.")
    @GetMapping("/{balanceId}")
    public ResponseEntity<BalanceDto.BalanceRes> getDetailBalance(final @PathVariable Long balanceId) {
        return new ResponseEntity<>(balanceService.getBalances(balanceId), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @Operation(summary = "밸런스 게임 투표", description = "밸런스 게임에 투표한다.",
            security = {@SecurityRequirement(name = "bearerAuth")})
    @PostMapping("/vote")  // 밸런스 게임 등록
    public ResponseEntity<?> voteBalanceGame(final @Valid @RequestBody BalanceDto.VoteBalanceReq rq) {
        return new ResponseEntity<>(balanceService.voteBalanceGame(rq), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @Operation(summary = "사용자 밸런스 게임 참여 여부 조회", description = "특정 밸런스 게임에 대해 특정 사용자가 어디에 참여했는지 확인한다.",
            security = {@SecurityRequirement(name = "bearerAuth")})
    @GetMapping("/vote/{balanceId}")
    public ResponseEntity<?> getWhereUserVote(final @RequestParam Long balanceId) {
        return new ResponseEntity<>(balanceService.getWhereUserVote(balanceId), HttpStatus.OK);
    }
}
