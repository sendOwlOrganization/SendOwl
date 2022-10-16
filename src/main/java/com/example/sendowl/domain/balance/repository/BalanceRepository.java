package com.example.sendowl.domain.balance.repository;

import com.example.sendowl.domain.balance.dto.BalanceCount;
import com.example.sendowl.domain.balance.entity.Balance;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BalanceRepository  extends JpaRepository<Balance, Long>, JpaSpecificationExecutor {
    @Query(value = "select new com.example.sendowl.domain.balance.dto.BalanceCount(" +
            "B.id, B.title, B.firstDetail, B.secondDetail, " +
            "sum(case when BC.pick='A' then 1 else 0 end), " +
            "sum(case when BC.pick='B' then 1 else 0 end)) " +
            "from Balance B left join BalanceCheck BC on B = BC.balance " +
            "where B.isDeleted=false " +
            "group by B " +
            "order by B.regDate desc"
            ,
            countQuery = "select count(B) " +
            "from Balance B left join BalanceCheck BC on B = BC.balance " +
            "where B.isDeleted=false " +
            "group by B " +
            "order by B.regDate desc"
    )
    List<BalanceCount> getAllBalanceCount(Pageable pageable);

    @Query(value = "select new com.example.sendowl.domain.balance.dto.BalanceCount(" +
            "B.id, B.title, B.firstDetail, B.secondDetail, " +
            "sum(case when BC.pick='A' then 1 else 0 end), " +
            "sum(case when BC.pick='B' then 1 else 0 end)) " +
            "from Balance B left join BalanceCheck BC on B = BC.balance " +
            "where B.isDeleted=false and B.id=:balanceId")
    Optional<BalanceCount> getBalanceCount(Long balanceId);
}
