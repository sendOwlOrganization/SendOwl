package com.example.sendowl.util;


import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.nio.charset.StandardCharsets;

// MessageListener를 이용하여 redis에서 pub되는 메세지는 받는다.
public class RedisMessageSubscriber implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] bytes) { // Callback for processing received objects through Redis.

        String body = new String(message.getBody(), StandardCharsets.UTF_8);
        String channel = new String(message.getChannel(), StandardCharsets.UTF_8);

        if(channel.split(":")[1].matches("expired")){
            // 만료된 경우 DB에 조회수를 증가시키는 코드 추가하기
            System.out.println("find!!!!");
            System.out.println("body:"+ body);
            System.out.println("channel:"+ channel);
        }
        else{
            return;
        }
   }
}

