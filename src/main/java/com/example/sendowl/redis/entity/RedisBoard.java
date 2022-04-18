package com.example.sendowl.redis.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;
import java.time.LocalDateTime;


@Getter
@Setter
@RedisHash(value = "board") // value + hash로 key값이 설정된다.
public class RedisBoard {
    @Id // 임시값으로 저장된다.
    private Long id; // 게시판의 아이디
    private Long count;
    private LocalDateTime createdAt;

    public RedisBoard(Long id) {
        this.id = id;
        this.count = 0L;
        this.createdAt = LocalDateTime.now();
    }
}
