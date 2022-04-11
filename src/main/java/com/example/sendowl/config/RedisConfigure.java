package com.example.sendowl.config;

import com.example.sendowl.entity.RedisBoard;
import com.example.sendowl.util.RedisMessageSubscriber;
import com.example.sendowl.util.RedisShadowkey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories
public class RedisConfigure {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.pattern}")
    private String pattern;

    @Bean
    public RedisConnectionFactory redisConnectionFactory(){
        return new LettuceConnectionFactory(host, port);
    }

    @Bean
    public RedisMessageSubscriber redisMessageSubscriber(){
        return new RedisMessageSubscriber();
    }
    @Bean
    public MessageListenerAdapter messageListenerAdapter(){
        return new MessageListenerAdapter(redisMessageSubscriber());
    }

    @Bean
    public RedisMessageListenerContainer redisContainer() { // Container providing asynchronous behaviour for Redis message listeners
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory());
        container.addMessageListener(messageListenerAdapter(), topic());
        return container;
    }

    @Bean
    public PatternTopic topic() {
        return new PatternTopic(pattern);
    }

    @Bean
    public RedisShadowkey redisShadowkey(){
        return new RedisShadowkey();
    }

    @Bean // Redis 템플릿을 어디서든 사용할 수 있도록 Bean객체를 생성해준다.
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        return redisTemplate;
    }
}
