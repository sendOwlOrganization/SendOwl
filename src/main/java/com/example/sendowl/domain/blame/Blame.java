package com.example.sendowl.domain.blame;

import com.example.sendowl.common.entity.BaseEntity;
import com.example.sendowl.domain.user.entity.User;

import javax.persistence.*;

@Entity
public class Blame extends BaseEntity {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userId;


    private String blameDetails;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User targetUserId;

    private Long contentsType;
    private Long contentsId;
    private String contentsDetails;

}
