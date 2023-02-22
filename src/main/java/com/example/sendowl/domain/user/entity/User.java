package com.example.sendowl.domain.user.entity;

import com.example.sendowl.common.entity.BaseEntity;
import com.example.sendowl.domain.board.entity.Board;
import com.example.sendowl.domain.like.entity.BoardLike;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(indexes = @Index(name = "idx_user", columnList = "email"))
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // null 넣으면 DB가 알아서 autoincrement해준다.
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String email;
    @Column(nullable = true)
    private String transactionId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    private String nickName;
    private Integer age;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Gender gender = Gender.OTHER;
    private String mbti;
    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.USER;
    private String introduction;
    private String refreshToken;
    private String profileImage;

    private LocalDateTime accessDate;
    private Long accessCount;
    @Column(columnDefinition = "bigint default 0")
    private Long exp = 0L;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Board> boardList;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BoardLike> boardLikeList;

    public void addExp(Long exp) {
        this.exp += exp;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void updateProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setMbti(String mbti) {
        this.mbti = mbti;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
