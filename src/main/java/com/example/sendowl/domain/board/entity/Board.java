package com.example.sendowl.domain.board.entity;

import com.example.sendowl.common.entity.BaseEntity;
import com.example.sendowl.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(indexes = @Index(name = "idx_board", columnList = "title"))
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String title;

    private String content;

    private String refinedContent;

    @Column(columnDefinition = "integer default 0")
    private Integer hit;

    @Formula("(select count(*) from board_like bl where bl.board_id = board_id)")
    private Long LikeCount;

    @Formula("(select count(*) from comment c where c.board_id = board_id)")
    private Long CommentCount;

    public void setHit(Integer hit) {
        this.hit = hit;
    }

    public void updateBoard(String title, String content, String refinedContent) {
        this.title = title;
        this.content = content;
        this.refinedContent = refinedContent;
    }
}
