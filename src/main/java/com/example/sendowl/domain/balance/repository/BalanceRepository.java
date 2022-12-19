package com.example.sendowl.domain.balance.repository;

import com.example.sendowl.domain.balance.entity.Balance;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BalanceRepository extends JpaRepository<Balance, Long>, JpaSpecificationExecutor {
    @Query("select b from Balance b join fetch b.balanceOptionList")
    Optional<Balance> findByIdWIthFetchJoin(Long balanceId);

    @Query(value = "select b " +
            "from Balance b join fetch b.balanceOptionList " +
            "where b.isDeleted=false " +
            "order by b.regDate desc"
            ,
            countQuery = "select count(b) " +
                    "from Balance b join fetch b.balanceOptionList " +
                    "where b.isDeleted=false " +
                    "order by b.regDate desc"
    )
    List<Balance> findAllByWithFetchJoin(Pageable pageable);
}
