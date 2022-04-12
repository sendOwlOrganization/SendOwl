package com.example.sendowl.entity.board;

import com.example.sendowl.entity.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class BoardLike {

    @Id
    @GeneratedValue
    @Column(name = "board_hit_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    public BoardLike(User user, Board board) {
        this.user = user;
        this.board = board;
    }

    public void addUser(User user) {
        this.user = user;
        if (!user.getBoardLikeList().contains(this)) {
            user.getBoardLikeList().add(this);
        }
    }

    public void addBoard(Board board) {
        this.board = board;
        if (!board.getBoardLikeList().contains(this)) {
            board.getBoardLikeList().add(this);
        }
    }

    public void remove() {
        user.getBoardLikeList().remove(this);
        board.getBoardLikeList().remove(this);
    }
}
