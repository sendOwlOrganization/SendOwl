package com.example.sendowl.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Builder
@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor // final이 붙은 변수 전부 생성자 등록
@Table(name="tb_member")
public class Member implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // null 넣으면 DB가 알아서 autoincrement해준다.
    private Long id;

    @Column(nullable = true)
    private String memId;// 대문자로 되면 알아서 user_id를 찾는다.

    @Column(nullable = true)
    @JsonIgnore
    private String memPw;
    private String memName;
    private String memEmail;
    private String memType;
    private String memMemo;

    private String regIp;
    private LocalDateTime regDate;
    private String modIp;
    private String modDate;
    @ColumnDefault("1")
    private Long active;
    @JsonIgnore
    private String accessToken;
    @JsonIgnore
    private String refreshToken;


    @ElementCollection(fetch=FetchType.LAZY)
    @Builder.Default
    @JsonIgnore
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collectors = new ArrayList<>();
        collectors.add(() -> {
            return "ROLE_USER";
        });
        return collectors;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return memPw;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return memEmail;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
