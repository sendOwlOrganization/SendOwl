package com.example.sendowl.redis.template;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisShadow {

    private RedisTemplate redisTemplate;
    private ValueOperations<String, String> valueOperations;
    private String prefixKey = "shadow:";

    public RedisShadow(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.valueOperations = redisTemplate.opsForValue();
    }
    
    public String findByKey(String key){
        return valueOperations.get(prefixKey+key);
    }
    public void set(String key, String value, Long exTime){
        valueOperations.set(prefixKey+key,value);
        valueOperations.getAndExpire(prefixKey+key, exTime, TimeUnit.SECONDS);
    }
    public void set(String key, Long exTime){
        valueOperations.set(prefixKey+key,"");
        valueOperations.getAndExpire(prefixKey+key, exTime, TimeUnit.SECONDS);
    }
    public void setTtl(String key, Long ttl){
        valueOperations.getAndExpire(prefixKey+key,ttl, TimeUnit.SECONDS);
    }
    public void delete(String key){
        valueOperations.getAndDelete(prefixKey+key);
    }
}
