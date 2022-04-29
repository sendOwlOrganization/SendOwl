package com.example.sendowl.redis;

import com.example.sendowl.redis.exception.RedisBoardNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

import static com.example.sendowl.domain.board.exception.enums.BoardErrorCode.*;

@SpringBootTest
public class RedisRepositoryTest {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    void repoTest() {
        //redisBoardRepository.delete(redisBoard);

    }

    @Test
    void repoShadowKeyTest() {
    }

    @Test
    void AddShadowkey(){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("shadowKey:board:1", "");
        valueOperations.getAndExpire("shadowKey:board:1", 60, TimeUnit.SECONDS);
    }

    @Test
    void AddBoardCount() {
    }
}