package com.example.sendowl.api.service;

import com.example.sendowl.domain.balance.dto.BalanceDto;
import com.example.sendowl.domain.balance.entity.Balance;
import com.example.sendowl.domain.balance.entity.BalanceOption;
import com.example.sendowl.domain.balance.entity.BalanceSelect;
import com.example.sendowl.domain.balance.exception.BalanceNotFoundException;
import com.example.sendowl.domain.balance.exception.enums.BalanceErrorCode;
import com.example.sendowl.domain.balance.repository.BalanceOptionRepository;
import com.example.sendowl.domain.balance.repository.BalanceRepository;
import com.example.sendowl.domain.balance.repository.BalanceSelectRepository;
import com.example.sendowl.domain.user.entity.User;
import com.example.sendowl.util.mail.JwtUserParser;
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
    private final BalanceOptionRepository balanceOptionRepository;
    private final BalanceSelectRepository balanceSelectRepository;

    private final JwtUserParser jwtUserParser;

    @Transactional
    public Long insertBalance(BalanceDto.InsertBalanceReq rq) {

        Balance balance = balanceRepository.save(Balance.builder()
                .balanceTitle(rq.getBalanceTitle())
                .build());

        List<BalanceDto.InsertBalanceOptionReq> optionReqs = rq.getInsertBalanceOptionReqs();
        for (BalanceDto.InsertBalanceOptionReq req : optionReqs) {
            balanceOptionRepository.save(
                    BalanceOption.builder()
                            .balance(balance)
                            .optionTitle(req.getTitle())
                            .hit(0L).build());
        }

        return balance.getId();
    }

    @Transactional
    public Long deleteBalance(Long id) {
        Balance balance = balanceRepository.findById(id).orElseThrow(() -> new BalanceNotFoundException(BalanceErrorCode.NOTFOUND));
        balance.delete();
        return balance.getId();
    }

    public BalanceDto.BalanceRes getBalances(Long balanceId) {
        Balance balance = balanceRepository.findByIdWIthFetchJoin(balanceId).orElseThrow(() -> new BalanceNotFoundException(BalanceErrorCode.NOTFOUND));
        return new BalanceDto.BalanceRes(balance);
    }

    public BalanceDto.GetAllBalanceRes getAllBalances() {
        List<Balance> balances = balanceRepository.findAllByWithFetchJoin(PageRequest.of(0, 10));
        return new BalanceDto.GetAllBalanceRes(balances);
    }


    @Transactional
    public Long voteBalanceGame(BalanceDto.VoteBalanceReq rq) {
        User user = jwtUserParser.getUser();

        // 밸런스 게임 존재 여부 확인
        Balance balance = balanceRepository.findById(rq.getBalanceId()).orElseThrow(
                () -> new BalanceNotFoundException(BalanceErrorCode.NOTFOUND)
        );

        // 해당 유저가 밸런스 게임에 참여 했는지 확인
        Optional<BalanceSelect> balanceSelectOptional = balanceSelectRepository.findByBalanceAndUser(balance, user);

        BalanceOption balanceOption = balanceOptionRepository.findById(rq.getBalanceOptionId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 밸런스 선택지 아이디입니다."));

        if (balanceSelectOptional.isEmpty()) {// 해당 밸런스 게임에 첫 참여인 경우
            balanceOption.addHit();
            return balanceSelectRepository.save(
                    BalanceSelect
                            .builder()
                            .balance(balance)
                            .balanceOption(balanceOption)
                            .user(user)
                            .build()).getId();
        } else {// 해당 밸런스 게임에 이미 참여한 적이 있는 경우
            BalanceSelect balanceSelect = balanceSelectOptional.get();

            balanceSelect.getBalanceOption().subHit(); // 기존 선택지의 값을 줄임
            balanceOption.addHit();
            balanceSelect.setBalanceOption(balanceOption);

            return balanceSelect.getId();
        }
    }

    public BalanceDto.BalanceOptionRes getWhereUserVote(Long balanceId) {
        User user = jwtUserParser.getUser();

        Balance balance = balanceRepository.findById(balanceId).orElseThrow(
                () -> new BalanceNotFoundException(BalanceErrorCode.NOTFOUND)
        );

        BalanceSelect balanceSelect = balanceSelectRepository.findByBalanceAndUser(balance, user).orElseThrow(
                () -> new BalanceNotFoundException(BalanceErrorCode.NOTFOUND)
        );

        BalanceOption balanceOption = balanceSelect.getBalanceOption();
        return new BalanceDto.BalanceOptionRes(
                balanceOption.getId(),
                balanceOption.getOptionTitle(),
                balanceOption.getHit());
    }
}
