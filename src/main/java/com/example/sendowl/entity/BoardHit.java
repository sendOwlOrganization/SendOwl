package com.example.sendowl.entity;

import com.example.sendowl.entity.User.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class BoardHit {

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
    public BoardHit(User user, Board board) {
        this.user = user;
        this.board = board;
    }

    public void addUser(User user) {
        this.user = user;
        if (!user.getBoardHitList().contains(this)) {
            user.getBoardHitList().add(this);
        }
    }

    public void addBoard(Board board) {
        this.board = board;
        if (!board.getBoardHitList().contains(this)) {
            board.getBoardHitList().add(this);
        }
    }
}
