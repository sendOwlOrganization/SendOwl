package com.example.sendowl.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class KafkaDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EmailVerifyDto {
        private String email;
        private String token;
    }
}
