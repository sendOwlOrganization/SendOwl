package com.example.sendowl.redis.entity;

import com.example.sendowl.redis.enums.RedisEnum;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.GeneratedValue;

@Getter
@RedisHash(value = RedisEnum.USER_TOKEN, timeToLive = 180) // timeToLive 으로 expire 설정
public class RedisUserToken {

    @Id
    @GeneratedValue
    private Long id;
    @Indexed
    private String email;

    private String token;

    @Builder
    public RedisUserToken(String email, String token) {
        this.email = email;
        this.token = token;
    }

    @Override
    public String toString() {
        return "RedisUserToken{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
