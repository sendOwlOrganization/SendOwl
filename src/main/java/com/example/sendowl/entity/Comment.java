package com.example.sendowl.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor // final이 붙은 변수 전부 생성자 등록
@Builder
@Getter
@Setter
@Table(name="tb_comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // null 넣으면 DB가 알아서 autoincrement해준다.
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 지연로딩으로 필요할때 불러온다.
    @JoinColumn(name = "board_id")
    private Board board;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private Long parentId;
    private Long depth;
    private Long ord;
    private String content;

    private String regIp;

    private LocalDateTime regDate;
    private int active;

}
