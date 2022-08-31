<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 248c94f (feat: Balance BalanceCheck entity)
package com.example.sendowl.domain.balance.entity;

import com.example.sendowl.common.entity.BaseEntity;
import com.example.sendowl.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BalanceCheck extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "balance_id")
    private Balance balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private String pick;

    public void setPick(String pick) {
        this.pick = pick;
    }
<<<<<<< HEAD
=======
package com.example.sendowl.domain.balance.entity;public class BalanceCheck {
>>>>>>> 799c1e2 (feat: BalanceController)
=======
>>>>>>> 248c94f (feat: Balance BalanceCheck entity)
}
