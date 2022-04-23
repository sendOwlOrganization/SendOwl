package com.example.sendowl.redis.template;

import com.example.sendowl.redis.enums.RedisEnum;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Optional;

public class RedisBoard {

    private RedisTemplate redisTemplate;
    private ValueOperations<String, String> valueOperations;

    private RedisShadow redisShadow;
    private String prefixKey = RedisEnum.BOARD+":"; // "board:"
    private Long ttl = 60L;

    public RedisBoard(RedisTemplate redisTemplate, RedisShadow redisShadow) {
        this.redisTemplate = redisTemplate;
        this.valueOperations = redisTemplate.opsForValue();
        this.redisShadow = redisShadow;
    }

    public Optional<String> getHit(Long id){
        return Optional.ofNullable(valueOperations.get(prefixKey + Long.toString(id)));
    }

    // key의 카운트를 반환
    public void setIfAbsent(Long id){
        String key = prefixKey + String.valueOf(id);
        if(!valueOperations.setIfAbsent(key, "1")){
            valueOperations.increment(prefixKey +Long.toString(id));
        }else{
            setShadowKeyTtl(id);
        }
    }

    // 해당 key를 삭제
    public boolean delete(Long id){
        return redisTemplate.delete(prefixKey + Long.toString(id));
    }

    public void setShadowKeyTtl(Long id){
        redisShadow.set(prefixKey + String.valueOf(id), ttl);
    }
}
