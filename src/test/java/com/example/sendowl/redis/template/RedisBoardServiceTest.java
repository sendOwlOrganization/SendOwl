package com.example.sendowl.redis.template;

import com.example.sendowl.redis.service.RedisBoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedisBoardServiceTest {

    @Autowired
    private RedisBoardService redisBoardService;

    @Test
    public void RedisBoardAdd(){
        redisBoardService.setAddCount(1L);
    }
    @Test
    public void RedisDelete(){
        redisBoardService.delete(1L);
    }
    @Test
    public void Redis(){
        redisBoardService.setAddCount(2L);
    }
    @Test
    public void RedisGetHit(){
        redisBoardService.getHit(1L);
    }

}
