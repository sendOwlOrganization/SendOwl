package com.example.sendowl.domain.blame;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class BlameType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String name;

    @Builder
    public BlameType(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
