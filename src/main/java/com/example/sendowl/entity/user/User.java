package com.example.sendowl.entity.user;

import com.example.sendowl.entity.BaseEntity;
import com.example.sendowl.entity.board.Board;
import com.example.sendowl.entity.board.BoardLike;
import com.example.sendowl.entity.comment.CommentLike;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseEntity implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // null 넣으면 DB가 알아서 autoincrement해준다.
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;
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
    public User(String email, String password, String name, String nickName,Role role, String introduction, String profileImage) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickName = nickName;
        this.role = role;
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
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collectors = new ArrayList<>();
        collectors.add(() -> {
            return "ROLE_USER";
        });
        return collectors;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
