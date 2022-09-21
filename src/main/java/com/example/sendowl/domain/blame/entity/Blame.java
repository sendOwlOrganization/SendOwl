package com.example.sendowl.domain.blame.entity;

import com.example.sendowl.common.entity.BaseEntity;
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
    private Long userId;
    private Long blameType;
    private String blameDetails;
    private Long targetUserId;
    private Long contentsType;
    private Long contentsId;
    private String contentsDetails;

    @Builder
    public Blame(Long id, Long userId, Long blameType, String blameDetails, Long targetUserId, Long contentsType, Long contentsId, String contentsDetails) {
        this.id = id;
        this.userId = userId;
        this.blameType = blameType;
        this.blameDetails = blameDetails;
        this.targetUserId = targetUserId;
        this.contentsType = contentsType;
        this.contentsId = contentsId;
        this.contentsDetails = contentsDetails;
    }

}
