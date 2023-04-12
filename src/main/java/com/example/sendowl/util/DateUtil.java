package com.example.sendowl.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class DateUtil {

    public LocalDateTime getTodayLocalDateTime() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime today = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 0, 0, 0);
        return today;
    }

    public LocalDateTime getTomorrowLocalDateTime() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime today = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 0, 0, 0);
        LocalDateTime tomorrow = today.plusDays(1L);
        return tomorrow;
    }

    public LocalDateTime getNowLocalDateTime() {
        LocalDateTime localDateTime = new Date().toInstant() // Date -> Instant
                .atZone(ZoneId.systemDefault()) // Instant -> ZonedDateTime
                .toLocalDateTime(); // ZonedDateTime -> LocalDateTime
        return localDateTime;
    }

}
