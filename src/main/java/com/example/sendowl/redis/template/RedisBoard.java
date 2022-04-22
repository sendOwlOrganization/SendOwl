package com.example.sendowl.redis.template;

import com.example.sendowl.redis.enums.RedisEnum;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class RedisBoard {

    private RedisTemplate redisTemplate;
    private ValueOperations<String,String> valueOperations;
    private RedisShadow redisShadow;
    private String prefixKey = RedisEnum.BOARD+":"; // "board:"
    private Long hit = 1L;
    private Long ttl = 60L;

    public RedisBoard(RedisTemplate redisTemplate, RedisShadow redisShadow) {
        this.redisTemplate = redisTemplate;
        this.valueOperations = redisTemplate.opsForValue();
        this.redisShadow  = redisShadow;
    }

    // key의 카운트를 반환
    public Long setIfAbsent(Long id){
        String key = prefixKey +Long.toString(id);
        if(!valueOperations.setIfAbsent(key, Long.toString(hit))){
            this.hit = valueOperations.increment(key);
        }
        return hit;
    }

    // 해당 key를 삭제
    public boolean delete(Long id){
        String key = prefixKey + Long.toString(id);
        return redisTemplate.delete(key);
    }

    public void setShadowKeyTtl(Long id){
        String key = prefixKey + Long.toString(id);
        redisShadow.set(key, ttl);
    }
}
