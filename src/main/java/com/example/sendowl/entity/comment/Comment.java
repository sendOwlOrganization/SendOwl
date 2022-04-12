package com.example.sendowl.entity.comment;

import com.example.sendowl.entity.BaseEntity;
import com.example.sendowl.entity.board.Board;
import com.example.sendowl.entity.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
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

    private Long parentId; // 대댓글 self 참조 부분은 미정입니다.

    private Long depth;

    @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CommentLike> commentLikeList = new ArrayList<>();

    @Builder
    public Comment(Board board, User user, String content, Long parentId, Long depth) {
        this.board = board;
        this.user = user;
        this.content = content;
        this.parentId = parentId;
        this.depth = depth;
    }
}
