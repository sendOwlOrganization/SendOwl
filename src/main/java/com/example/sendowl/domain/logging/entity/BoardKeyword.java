package com.example.sendowl.domain.logging.entity;

import com.example.sendowl.common.entity.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name="log_board_keyword")
public class BoardKeyword extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    private Long categoryId;

    private String mbti;

    private String keyword;

    @Builder
    public BoardKeyword(Long categoryId, String keyword, String mbti){
        this.categoryId = categoryId;
        this.keyword = keyword;
        this.mbti = mbti;
    }

}
