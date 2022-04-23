package com.example.sendowl.redis.template;

import com.example.sendowl.redis.enums.RedisEnum;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

public class RedisShadow {

    private RedisTemplate redisTemplate;
    private ValueOperations<String, String> valueOperations;
    private String prefixKey = RedisEnum.SHADOW+":";// "shadow:"
    private String value = "";

    public RedisShadow(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.valueOperations = redisTemplate.opsForValue();
    }

    public String findByKey(String key){
        return valueOperations.get(prefixKey+key);
    }
    public void set(String key, Long ttl){
        valueOperations.set(prefixKey+key,value, ttl, TimeUnit.SECONDS);
    }
    public void delete(String key){
        valueOperations.getAndDelete(prefixKey+key);
    }
}
