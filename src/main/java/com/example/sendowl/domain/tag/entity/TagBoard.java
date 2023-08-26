package com.example.sendowl.domain.tag.entity;

import com.example.sendowl.domain.board.entity.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class TagBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_board_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    public TagBoard(Tag tag, Board board) {
        this.tag = tag;
        this.board = board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}
