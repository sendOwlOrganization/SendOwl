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
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // null 넣으면 DB가 알아서 autoincrement해준다.
    private Long id;

    @Column(nullable = true)
    private String memId;// 대문자로 되면 알아서 user_id를 찾는다.

    @Column(nullable = true)
    private String memPw;
    private String memName;
    private String memEmail;
    private String memType;
    private String memMemo;

    private String regIp;
    private String regDate;
    private String modIp;
    private String modDate;

    private String accessToken;
    private String refreshToken;

    private String active;

}
