package com.example.sendowl.kafka.producer;

public interface KafkaProducer {

    void sendEmailVerification(String email, String token);
}
