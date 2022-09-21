package com.example.sendowl.domain.blame.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlameType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String name;
    @Builder.Default
    private Boolean isDeleted = false;

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public void setName(String name) {
        this.name = name;
    }
}
