package com.example.sendowl.config;

import com.example.sendowl.domain.board.repository.BoardRepository;
import com.example.sendowl.redis.sub.RedisMessageSubscriber;
import com.example.sendowl.redis.service.RedisBoardService;
import com.example.sendowl.redis.service.RedisEmailTokenService;
import com.example.sendowl.redis.service.RedisShadowService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@RequiredArgsConstructor
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
    public RedisEmailTokenService redisEmailToken() {
        return new RedisEmailTokenService(redisTemplate(redisConnectionFactory()));
    }

    @Bean
    public RedisBoardService redisBoard(){
        return new RedisBoardService(redisTemplate(redisConnectionFactory()), redisShadow());
    }

    @Bean
    public RedisShadowService redisShadow(){
        return new RedisShadowService(redisTemplate(redisConnectionFactory()));
    }

    private final BoardRepository boardRepository;

    @Bean
    public RedisMessageSubscriber redisMessageSubscriber(){
        return new RedisMessageSubscriber();
    }

    @Bean
    public MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter(redisMessageSubscriber());
    }

    @Bean
    public RedisMessageListenerContainer redisContainer() { // Container providing asynchronous behaviour for Redis message listeners
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory());
        container.addMessageListener(messageListener(), topic());
        return container;
    }

    @Bean
    public PatternTopic topic() {
        return new PatternTopic(pattern);
    }


    @Bean // Redis 템플릿을 어디서든 사용할 수 있도록 Bean객체를 생성해준다.
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        // 데이터의 직렬화, 역지결화시 사용하는 방식.
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setEnableTransactionSupport(true);
        return redisTemplate;
    }
}
