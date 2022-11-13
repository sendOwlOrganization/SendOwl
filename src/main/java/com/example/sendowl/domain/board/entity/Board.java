package com.example.sendowl.domain.board.entity;

import com.example.sendowl.common.entity.BaseEntity;
import com.example.sendowl.domain.category.entity.Category;
import com.example.sendowl.domain.like.entity.BoardLike;
import com.example.sendowl.domain.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@NoArgsConstructor
@Entity
@Table(indexes = @Index(name="idx_board", columnList = "title"))
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(columnDefinition = "integer default 0")
    private Integer hit;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BoardLike> boardLikeList = new ArrayList<>();

    @Builder
    public Board(User user, String title, String content, String refinedContent, Category category, Integer hit) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.refinedContent = refinedContent;
        this.category = category;
        this.hit = hit;
    }

    public void setHit(Integer hit) {
        this.hit = hit;
    }

    public void updateBoard(String title, String content, Category category, String refinedContent) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.refinedContent = refinedContent;
    }

    @Override
    public String toString() {
        return "Board{" +
                "user=" + user +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", category=" + category +
                ", hit=" + hit +
                '}';
    }
}
