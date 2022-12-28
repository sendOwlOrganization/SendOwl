package com.example.sendowl.domain.logging.entity;

import com.example.sendowl.common.entity.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name="log_board")
public class BoardLogs extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long seq;

    private Long categoryId;

    private String mbti;

    @Builder
    public BoardLogs(Long categoryId, String mbti) {
        this.categoryId = categoryId;
        this.mbti=mbti;
    }
}
