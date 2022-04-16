package com.example.sendowl.domain.user.entity;

import com.example.sendowl.common.entity.BaseEntity;
import com.example.sendowl.domain.board.entity.Board;
import com.example.sendowl.domain.board.entity.BoardLike;
import com.example.sendowl.domain.comment.entity.CommentLike;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // null 넣으면 DB가 알아서 autoincrement해준다.
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    private String nickName;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    private String introduction;
    private String refreshToken;
    private String profileImage;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Board> boardList = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BoardLike> boardLikeList = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CommentLike> commentLikeList = new ArrayList<>();

    @Builder
    public User(String email, String password, String name, String nickName, String introduction, String profileImage) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickName = nickName;
        this.introduction = introduction;
        this.profileImage = profileImage;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void updateProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                ", role=" + role +
                ", introduction='" + introduction + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", profileImage='" + profileImage + '\'' +
                '}';
    }
}
