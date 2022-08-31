package com.example.sendowl.domain.balance.entity;

import com.example.sendowl.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Balance extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String title;
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

    @Override
    public String toString() {
        return "Balance{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", aDetail='" + aDetail + '\'' +
                ", bDetail='" + bDetail + '\'' +
                '}';
    }
}
