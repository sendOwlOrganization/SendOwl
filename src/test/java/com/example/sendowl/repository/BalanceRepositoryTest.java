package com.example.sendowl.repository;

import com.example.sendowl.domain.balance.dto.BalanceCount;
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
    public void GetAllBalanceCountTest() {
        List<BalanceCount> balanceCounts = balanceRepository.getAllBalanceCount(PageRequest.of(0, 10));

    }

    @Test
    public void GetDetailBalanceCountTest() {
        Optional<BalanceCount> balanceCount = balanceRepository.getBalanceCount(1L);
        balanceCount.get().toString();
    }
}