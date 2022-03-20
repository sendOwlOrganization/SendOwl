package com.example.sendowl.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
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
@DynamicInsert
@Table(name="tb_board")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    @JoinColumn
    private String regId;
    private String regIp;
    @ColumnDefault("CURRENT_TIMESTAMP")
    private String regDate;
    private String modId;
    private String modIp;
    private String modDate;
    @JoinColumn
    private long cateId;
    @ColumnDefault("0")
    private int hit;
    @ColumnDefault("'Y'")
    private String active;

}
