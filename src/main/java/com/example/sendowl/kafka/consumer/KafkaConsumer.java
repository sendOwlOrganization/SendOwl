package com.example.sendowl.kafka.consumer;

import com.example.sendowl.util.mail.MailDto;
import com.example.sendowl.util.mail.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;


import static com.example.sendowl.kafka.dto.KafkaDto.*;
import static com.example.sendowl.kafka.enums.KafkaTopic.*;
import static com.example.sendowl.kafka.enums.KafkaGroup.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final MailService mailService;

    @KafkaListener(topics = EMAIL_VERIFICATION, groupId = NOTIFY_EMAIL_VERIFICATION)
    public void sendEmailValidation(EmailVerifyDto dto) {
        new Thread(() -> {
            try {
                log.warn("kafka 이메일 전송 :" + dto.getEmail() + " " + dto.getToken());
                mailService.sendEmailVerification(MailDto.builder()
                        .address(dto.getEmail())
                        .title("[SendOwl] 이메일 인증 토큰")
                        .message(dto.getToken())
                        .build());
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
