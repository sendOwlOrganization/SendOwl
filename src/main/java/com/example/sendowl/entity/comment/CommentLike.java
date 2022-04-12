package com.example.sendowl.entity.comment;

import com.example.sendowl.entity.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class CommentLike {

    @Id
    @GeneratedValue
    @Column(name = "comment_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @Builder
    public CommentLike(User user, Comment comment) {
        this.user = user;
        this.comment = comment;
    }

    public void addUser(User user) {
        this.user = user;
        if (!user.getCommentLikeList().contains(this)) {
            user.getCommentLikeList().add(this);
        }
    }

    public void addBoard(Comment comment) {
        this.comment = comment;
        if (!comment.getCommentLikeList().contains(this)) {
            comment.getCommentLikeList().add(this);
        }
    }

    public void remove() {
        user.getCommentLikeList().remove(this);
        comment.getCommentLikeList().remove(this);
    }
}
