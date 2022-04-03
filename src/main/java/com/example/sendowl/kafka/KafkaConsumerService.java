package com.example.sendowl.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "oingdaddy", groupId = "group-id-oing")
    public void consume(String message) throws IOException {
        System.out.println("receive message: " + message);
    }
}
