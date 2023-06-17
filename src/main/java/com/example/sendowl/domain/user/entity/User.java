package com.example.sendowl.domain.user.entity;

import com.example.sendowl.common.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
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

    private String email;
    private String transactionId;

    private String password;

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
    private LocalDateTime refreshTokenRegDate;

    private String profileImage;
    private LocalDateTime accessDate;
    private Long accessCount;
    @Column(columnDefinition = "bigint default 0")
    private Long exp = 0L;

    public void addExp(Long exp) {
        this.exp += exp;
    }
    @Override
    public void delete(){
        super.delete();
        this.email = null;
        this.transactionId = null;
        this.password = null;
        this.name = null;
        this.profileImage = null;
        this.birthday = null;
        this.age = null;
        this.introduction = null;
    }
}
