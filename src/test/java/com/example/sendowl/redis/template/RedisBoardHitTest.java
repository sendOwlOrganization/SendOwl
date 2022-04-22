package com.example.sendowl.redis.template;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
public class RedisBoardHitTest {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Test
    public void BoardHitAdd(){

    }
    @Test
    public void HashOperationTest(){
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();

        hashOperations.put("test","username","김재환");
        hashOperations.put("test","age","10");
        hashOperations.increment("test", "age", 3);
        hashOperations.delete("test", "age");

    }
    @Test
    public void ValueOperationTest(){
        redisTemplate.delete("test");
    }
}
