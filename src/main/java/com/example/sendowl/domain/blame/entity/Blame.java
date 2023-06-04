package com.example.sendowl.domain.blame.entity;

import com.example.sendowl.common.entity.BaseEntity;
import com.example.sendowl.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
public class Blame extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blame_id")
    private Long id;

    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(foreignKeyDefinition = "FOREIGN KEY (user_id) REFERENCES user (user_id) ON DELETE SET NULL"))
    private User user;

    @ManyToOne
    @JoinColumn(name = "blame_type_id")
    private BlameType blameType;
    private String blameDetails;

    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.DETACH)
    @JoinColumn(name = "target_user_id", foreignKey = @ForeignKey(foreignKeyDefinition = "FOREIGN KEY (target_user_id) REFERENCES user (user_id) ON DELETE SET NULL"))
    private User targetUser;

    @Enumerated(EnumType.STRING)
    private BlameContentType blameContentsType;
    private Long contentsId;
    private String contentsDetails;

}
