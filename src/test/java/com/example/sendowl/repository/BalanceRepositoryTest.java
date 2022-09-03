package com.example.sendowl.repository;

import com.example.sendowl.domain.balance.dto.BalanceCount;
import com.example.sendowl.domain.balance.repository.BalanceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@DataJpaTest(
        properties = {"spring.jpa.properties.hibernate.default_batch_fetch_size=1000"}
)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BalanceRepositoryTest {
    @Autowired
    private BalanceRepository balanceRepository;

    @Test
    public void BalanceCountTest(){
        List<BalanceCount> balanceCounts = balanceRepository.getBalanceCount(PageRequest.of(0,10));

        balanceCounts.forEach(balanceCount ->
                System.out.println(balanceCount.getId() + " / " +balanceCount.getTitle() + "/" + balanceCount.getACount() +" / " + balanceCount.getBCount()) );
    }
}
