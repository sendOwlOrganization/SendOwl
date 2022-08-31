<<<<<<< HEAD
<<<<<<< HEAD
package com.example.sendowl.domain.balance.entity;

import com.example.sendowl.common.entity.BaseEntity;
import lombok.*;
=======
package com.example.sendowl.domain.balance.entity;

import com.example.sendowl.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

>>>>>>> 248c94f (feat: Balance BalanceCheck entity)
import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
<<<<<<< HEAD
@Getter
@Setter
=======
>>>>>>> 248c94f (feat: Balance BalanceCheck entity)
public class Balance extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String title;
<<<<<<< HEAD
    private String firstDetail;
    private String secondDetail;
=======
    private String aDetail;
    private String bDetail;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setaDetail(String aDetail) {
        this.aDetail = aDetail;
    }

    public void setbDetail(String bDetail) {
        this.bDetail = bDetail;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getaDetail() {
        return aDetail;
    }

    public String getbDetail() {
        return bDetail;
    }
>>>>>>> 248c94f (feat: Balance BalanceCheck entity)

    @Override
    public String toString() {
        return "Balance{" +
                "id=" + id +
                ", title='" + title + '\'' +
<<<<<<< HEAD
                ", firstDetail='" + firstDetail + '\'' +
                ", secondDetail='" + secondDetail + '\'' +
                '}';
    }
=======
package com.example.sendowl.domain.balance.entity;public class Balance {
>>>>>>> 799c1e2 (feat: BalanceController)
=======
                ", aDetail='" + aDetail + '\'' +
                ", bDetail='" + bDetail + '\'' +
                '}';
    }
>>>>>>> 248c94f (feat: Balance BalanceCheck entity)
}
