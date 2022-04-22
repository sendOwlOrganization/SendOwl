package com.example.sendowl.redis.template;

import com.example.sendowl.redis.enums.RedisEnum;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class RedisBoard {

    private RedisTemplate redisTemplate;
    private ValueOperations<String,String> valueOperations;
    private String prefixKey = RedisEnum.BOARD;
    private Long hit;

    public RedisBoard(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.valueOperations = redisTemplate.opsForValue();
        this.hit = 1L;
    }

    // key의 카운트를 반환
    public Long setIfAbsent(Long id){
        String key = prefixKey + ":"+Long.toString(id);
        if(!valueOperations.setIfAbsent(key, Long.toString(hit))){
            this.hit = valueOperations.increment(key);
        }
        return hit;
    }

    // 해당 key를 삭제
    public boolean delete(Long id){
        String key = prefixKey + ":"+Long.toString(id);
        return redisTemplate.delete(key);
    }
}
