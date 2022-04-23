package com.example.sendowl.redis.template;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisBoardTest {

    @Autowired
    private RedisBoard redisBoard;

    @Test
    public void RedisBoardAdd(){
        redisBoard.setIfAbsent(1L);
    }
    @Test
    public void RedisDelete(){
        redisBoard.delete(1L);
    }
    @Test
    public void Redis(){
        redisBoard.setIfAbsent(2L);
    }
    @Test
    public void RedisGetHit(){
        redisBoard.getHit(1L);
    }

}
