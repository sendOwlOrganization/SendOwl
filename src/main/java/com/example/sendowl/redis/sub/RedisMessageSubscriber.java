package com.example.sendowl.redis.sub;

import com.example.sendowl.redis.enums.RedisEnum;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.nio.charset.StandardCharsets;

public class RedisMessageSubscriber implements MessageListener {

    public void onMessage(Message message, byte[] bytes) { // Callback for processing received objects through Redis.
        String channel = new String(message.getChannel(), StandardCharsets.UTF_8);
        String body = new String(message.getBody(), StandardCharsets.UTF_8);
        String event = channel.split(":")[1];
        if (event.equals(RedisEnum.EXPIRE)) {
            String[] bodyData = body.split(":");
            String prefix = bodyData[0];
            String key = bodyData[1];
        }
    }

}