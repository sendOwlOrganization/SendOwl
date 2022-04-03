package com.example.sendowl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SendOwlApplication {

    public static void main(String[] args) {
        SpringApplication.run(SendOwlApplication.class, args);
    }

}
