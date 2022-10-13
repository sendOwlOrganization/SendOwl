package com.example.sendowl.domain.blame.entity;

import com.example.sendowl.common.entity.BaseEntity;
import com.example.sendowl.domain.user.entity.User;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Blame extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private Long blameType; // 신고 종류
    private String blameDetails; // 신고 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_user_id")
    private User targetUser; // 신고당하는 사람 id
    private Long contentsType; // 게시글:1, 댓글:2 ...
    private Long contentsId; // 해당 콘텐츠의 id
    private String contentsDetails; // 신고하는 내용

    @Builder
    public Blame(Long id, User user, Long blameType, String blameDetails, User targetUser, Long contentsType, Long contentsId, String contentsDetails) {
        this.id = id;
        this.user = user;
        this.blameType = blameType;
        this.blameDetails = blameDetails;
        this.targetUser = targetUser;
        this.contentsType = contentsType;
        this.contentsId = contentsId;
        this.contentsDetails = contentsDetails;
    }

}
