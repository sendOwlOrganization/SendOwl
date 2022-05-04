package com.example.sendowl;

import com.example.sendowl.domain.category.entity.Category;
import com.example.sendowl.domain.category.entity.CategoryName;
import com.example.sendowl.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@SpringBootApplication
@RequiredArgsConstructor
public class SendOwlApplication {

    private final CategoryRepository categoryRepository;

    public static void main(String[] args) {
        SpringApplication.run(SendOwlApplication.class, args);
    }
}

