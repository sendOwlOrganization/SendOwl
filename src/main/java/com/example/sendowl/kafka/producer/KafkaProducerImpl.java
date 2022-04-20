package com.example.sendowl.kafka.producer;

import com.example.sendowl.kafka.dto.KafkaDto.EmailVerifyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.example.sendowl.kafka.enums.KafkaTopic.*;

@Service
@RequiredArgsConstructor
public class KafkaProducerImpl implements KafkaProducer {

    private final KafkaTemplate<String, EmailVerifyDto> emailVerifyTemplate;

    @Override
    public void sendEmailVerification(String email, String token) {
        emailVerifyTemplate.send(EMAIL_VERIFICATION, new EmailVerifyDto(email, token));
    }
}
