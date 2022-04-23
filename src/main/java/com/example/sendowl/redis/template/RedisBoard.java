package com.example.sendowl.redis.template;

import com.example.sendowl.redis.enums.RedisEnum;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

public class RedisBoard {

    private RedisTemplate redisTemplate;
    private ValueOperations<String, String> valueOperations;

    private RedisShadow redisShadow;
    private String prefixKey = RedisEnum.BOARD+":"; // "board:"
    private Long hit = 1L;
    private Long ttl = 60L;

    public RedisBoard(RedisTemplate redisTemplate, RedisShadow redisShadow) {
        this.redisTemplate = redisTemplate;
        this.valueOperations = redisTemplate.opsForValue();
        this.redisShadow = redisShadow;
    }

    public Long getHit(Long id){
        return Long.parseLong(valueOperations.get(prefixKey+Long.toString(id)));
    }

    // key의 카운트를 반환
    public Long setIfAbsent(Long id){
        System.out.println("r1");
        String key = prefixKey + String.valueOf(id);
        if(!valueOperations.setIfAbsent(key, hit.toString())){
            System.out.println("r2");
            hit = valueOperations.increment(prefixKey +Long.toString(id));
        }else{
            System.out.println("r3");
            setShadowKeyTtl(id);
        }
        System.out.println("r4");

        return hit;
    }

    // 해당 key를 삭제
    public boolean delete(Long id){
        return redisTemplate.delete(prefixKey + Long.toString(id));
    }

    public void setShadowKeyTtl(Long id){
        redisShadow.set(prefixKey + String.valueOf(id), ttl);
    }
}
