package com.example.sendowl.api.service;

import com.example.sendowl.domain.balance.dto.BalanceCount;
import com.example.sendowl.domain.balance.dto.BalanceDto;
import com.example.sendowl.domain.balance.entity.Balance;
import com.example.sendowl.domain.balance.entity.BalanceCheck;
import com.example.sendowl.domain.balance.exception.BalanceNotFoundException;
import com.example.sendowl.domain.balance.exception.enums.BalanceErrorCode;
import com.example.sendowl.domain.balance.repository.BalanceCheckRepository;
import com.example.sendowl.domain.balance.repository.BalanceRepository;
import com.example.sendowl.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
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
        balance.setFirstDetail(rq.getFirstDetail());
        balance.setSecondDetail(rq.getSecondDetail());
    }

    @Transactional
    public void deleteBalance(Long id) {
        Balance balance = balanceRepository.findById(id).orElseThrow(()->new BalanceNotFoundException(BalanceErrorCode.NOTFOUND));
        balance.delete();
    }

    public BalanceDto.GetAllBalanceRes getAllBalances() {
        List<BalanceCount> balances = balanceRepository.getAllBalanceCount(PageRequest.of(0,10));
        return new BalanceDto.GetAllBalanceRes(balances);
    }
    public BalanceDto.BalanceRes getBalances(Long balanceId) {
        BalanceCount balanceCount = balanceRepository.getBalanceCount(balanceId).orElseThrow(() -> new BalanceNotFoundException(BalanceErrorCode.NOTFOUND));
        return new BalanceDto.BalanceRes(balanceCount);
    }


    @Transactional
    public void voteBalanceGame(BalanceDto.VoteBalanceReq rq, User user) {
        // 밸런스 게임 존재 여부 확인
        Balance balance = balanceRepository.findById(rq.getBalanceId()).orElseThrow(() -> new BalanceNotFoundException(BalanceErrorCode.NOTFOUND));

        // 해당 유저가 밸런스 게임이 참여 했는지 확인
        Optional<BalanceCheck> balanceCheck = balanceCheckRepository.findByBalanceAndUser(balance, user);

        // 해당 밸런스 게임에 첫 참여인 경우
        if(balanceCheck.isEmpty()){
            balanceCheckRepository.save(
                    BalanceCheck
                            .builder()
                            .balance(balance)
                            .user(user)
                            .pick(rq.getPick())
                            .build());
        }else{// 해당 밸런스 게임에 이미 참여한 적이 있는 경우
            BalanceCheck savedBalanceCheck = balanceCheck.get();
            savedBalanceCheck.setPick(rq.getPick()); // 유저가 선택한 데이터로 갱신
        }

        // 해당 밸런스 게임의 최종 결과 반환

    }

}
