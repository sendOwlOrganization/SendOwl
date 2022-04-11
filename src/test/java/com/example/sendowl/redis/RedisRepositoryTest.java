package com.example.sendowl.redis;

import com.example.sendowl.entity.RedisBoard;
import com.example.sendowl.excption.RedisBoardNotFoundException;
import com.example.sendowl.repository.RedisBoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedisRepositoryTest {

    @Autowired
    private RedisBoardRepository redisBoardRepository;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    void repoTest() {
        RedisBoard redisBoard = new RedisBoard(2L);

        redisBoardRepository.save(redisBoard);

        redisBoardRepository.count();

        //redisBoardRepository.delete(redisBoard);

    }

    @Test
    void repoShadowKeyTest() {
    }

    @Test
    void DelShadowKeyTest() {
    }

    @Test
    void AddShadowkey(){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("shadowKey:board:1", "");
        valueOperations.getAndExpire("shadowKey:board:1", 60, TimeUnit.SECONDS);
    }

    @Test
    void AddBoardCount() {

        RedisBoard redisBoard = redisBoardRepository.findById(1L).orElseThrow(() -> new RedisBoardNotFoundException("해당 테이블이 존재하지 않습니다."));
        redisBoard.setCount(redisBoard.getCount() + 1);
        redisBoardRepository.save(redisBoard);
        System.out.println("redisBaordCount : " + redisBoard.getCount());
    }
}