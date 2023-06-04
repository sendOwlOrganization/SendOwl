package com.example.sendowl.domain.comment.entity;

import com.example.sendowl.common.entity.BaseEntity;
import com.example.sendowl.domain.board.entity.Board;
import com.example.sendowl.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(indexes = @Index(name = "idx_comment", columnList = "board_id")) // 이미 foreign key라 index가 있지만 이름 변경
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(foreignKeyDefinition = "FOREIGN KEY (user_id) REFERENCES user (user_id) ON DELETE SET NULL"))
    private User user;
    @Column(nullable = false)
    private String content;
    private Long depth;
    @Column(name = "parent_id")
    private Long parent;
    @Formula("(select count(*) from comment_like cl where cl.comment_id = comment_id and cl.is_deleted = 'N')")
    private Long likeCount;

    public void updateContent(String content) {
        this.content = content;
    }
}
