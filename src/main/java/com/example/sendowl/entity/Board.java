package com.example.sendowl.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="tb_board")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String title;
    private String content;
    @JoinColumn
    private String regId;
    private String regIp = "default test";
    private String regDate = "date()";
    private String modId  = "default test";
    private String modIp  = "default test";
    private String modDate  = "default test";
    @JoinColumn
    private long cateId = 1;
    private int hit  = 0;
    private String active  = "Y";

}
