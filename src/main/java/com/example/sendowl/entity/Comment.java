package com.example.sendowl.entity;

import com.example.sendowl.entity.user.User;
import lombok.*;

import javax.persistence.*;

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
    @JoinColumn(name = "member_id")
    private User user;

    @Column(nullable = false)
    private String content;

    private Long parentId; // 대댓글 self 참조 부분은 미정입니다.

    private Long depth;

    @Builder
    public Comment(Board board, User user, String content, Long parentId, Long depth) {
        this.board = board;
        this.user = user;
        this.content = content;
        this.parentId = parentId;
        this.depth = depth;
    }
}
