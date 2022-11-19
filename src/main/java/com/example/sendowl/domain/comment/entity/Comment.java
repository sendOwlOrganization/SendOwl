package com.example.sendowl.domain.comment.entity;

import com.example.sendowl.common.entity.BaseEntity;
import com.example.sendowl.domain.board.entity.Board;
import com.example.sendowl.domain.user.entity.User;
import lombok.*;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Table(indexes = @Index(name="idx_comment", columnList = "board_id")) // 이미 foreign key라 index가 있지만 이름 변경
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String content;

    private Long depth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Comment> children = new ArrayList<>();

    @Formula("(select count(*) from comment_like cl where cl.comment_id = comment_id)")
    private Long commentLikeCount;

    @Builder
    public Comment(Board board, User user, String content, Comment parent, Long depth) {
        this.board = board;
        this.user = user;
        this.content = content;
        this.parent = parent;
        this.depth = depth;
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
