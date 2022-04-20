package com.example.sendowl.redis;

import com.example.sendowl.redis.entity.RedisBoard;
import com.example.sendowl.redis.excption.RedisBoardNotFoundException;
import com.example.sendowl.redis.repository.RedisBoardRepository;
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
        redisBoardRepository.deleteById(1L);
    }

    @Test
    void AddShadowkey(){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("shadowKey:board:1", "");
        valueOperations.getAndExpire("shadowKey:board:1", 60, TimeUnit.SECONDS);
    }

    @Test
    void AddBoardCount() {

        RedisBoard redisBoard = redisBoardRepository.findById(1L).orElseThrow(() -> new RedisBoardNotFoundException(NOT_FOUND));
        redisBoard.setCount(redisBoard.getCount() + 1);
        redisBoardRepository.save(redisBoard);
        System.out.println("redisBaordCount : " + redisBoard.getCount());
    }
}