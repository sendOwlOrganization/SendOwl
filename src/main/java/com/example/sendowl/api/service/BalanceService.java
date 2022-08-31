package com.example.sendowl.api.service;

import com.example.sendowl.domain.balance.dto.BalanceDto;
import com.example.sendowl.domain.balance.entity.Balance;
import com.example.sendowl.domain.balance.entity.BalanceCheck;
import com.example.sendowl.domain.balance.exception.BalanceNotFoundException;
import com.example.sendowl.domain.balance.exception.enums.BalanceErrorCode;
import com.example.sendowl.domain.balance.repository.BalanceCheckRepository;
import com.example.sendowl.domain.balance.repository.BalanceRepository;
import com.example.sendowl.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BalanceService {

    private final BalanceRepository balanceRepository;
    private final BalanceCheckRepository balanceCheckRepository;

    @Transactional
    public void insertBalance(BalanceDto.InsertBalanceReq rq) {
        balanceRepository.save(rq.toEntity());
    }

    @Transactional
    public void updateBalance(BalanceDto.UpdateBalanceReq rq) {
        // 존재하는지 확인
        Balance balance = balanceRepository.findById(rq.getId()).orElseThrow(()->new BalanceNotFoundException(BalanceErrorCode.NOTFOUND));
        balance.setTitle(rq.getTitle());
        balance.setaDetail(rq.getaDetail());
        balance.setbDetail(rq.getbDetail());
    }

    @Transactional
    public void deleteBalance(Long id) {
        Balance balance = balanceRepository.findById(id).orElseThrow(()->new BalanceNotFoundException(BalanceErrorCode.NOTFOUND));
        balance.delete();
    }

    public BalanceDto.GetBalanceRes getBalances() {
        List<Balance> balances = balanceRepository.findTop10ByIsDeletedFalseOrderByRegDateDesc();
        return new BalanceDto.GetBalanceRes(balances);
    }

    @Transactional
    public void voteBalanceGame(BalanceDto.VoteBalanceReq rq, User user) {
        Balance balance = balanceRepository.findById(rq.getBalanceId()).orElseThrow(() -> new BalanceNotFoundException(BalanceErrorCode.NOTFOUND));

        Optional<BalanceCheck> balanceCheck = balanceCheckRepository.findByBalanceAndUser(balance, user);

        if(balanceCheck.isEmpty()){
            balanceCheckRepository.save(
                    BalanceCheck
                            .builder()
                            .balance(balance)
                            .user(user)
                            .pick(rq.getPick())
                            .build());
        }else{
            BalanceCheck savedBalanceCheck = balanceCheck.get();
            savedBalanceCheck.setPick(rq.getPick());
        }
    }
}
