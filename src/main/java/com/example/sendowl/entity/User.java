package com.example.sendowl.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor // final이 붙은 변수 전부 생성자 등록
@Table(name="tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // null 넣으면 DB가 알아서 autoincrement해준다.
    private Long id;

    @Column(nullable = true)
    private String userId;// 대문자로 되면 알아서 user_id를 찾는다.

    @Column(nullable = true)
    private String password;

    @Column(nullable = true)
    private String username;

    @Column(nullable = true)
    private String email;

    @Column(nullable = true)
    private String createdTime;
}
