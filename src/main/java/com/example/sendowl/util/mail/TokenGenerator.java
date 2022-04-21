package com.example.sendowl.util.mail;

import lombok.NoArgsConstructor;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

@NoArgsConstructor
public class TokenGenerator {
    private final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    public String generateNewToken() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[20];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    public String generateSixRandomNumber() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }
}