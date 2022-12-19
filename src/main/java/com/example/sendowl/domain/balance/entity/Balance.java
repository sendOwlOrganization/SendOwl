package com.example.sendowl.domain.balance.entity;

import com.example.sendowl.common.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Balance extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "balance_id", nullable = false)
    private Long id;
    private String balanceTitle;

    @OneToMany(mappedBy = "balance", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BalanceOption> balanceOptionList = new ArrayList<>();
}
