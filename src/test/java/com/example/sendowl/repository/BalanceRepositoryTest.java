package com.example.sendowl.repository;

import com.example.sendowl.domain.balance.entity.Balance;
import com.example.sendowl.domain.balance.repository.BalanceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

@DataJpaTest(
        properties = {"spring.jpa.properties.hibernate.default_batch_fetch_size=1000"}
)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BalanceRepositoryTest {
    @Autowired
    private BalanceRepository balanceRepository;

    @Test
    public void when_밸런스를_요청하면_then_밸런스를_반환한다() {
        Optional<Balance> byIdWIthFetchJoin = balanceRepository.findByIdWIthFetchJoin(1L);
    }

    @Test
    public void when_모든_밸런스를_요청하면_then_모든밸런스를_반환한다() {
        List<Balance> balanceCounts = balanceRepository.findAllByWithFetchJoin(PageRequest.of(0, 10));
    }
}