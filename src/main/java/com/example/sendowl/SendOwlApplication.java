package com.example.sendowl;

import com.example.sendowl.domain.category.entity.Category;
import com.example.sendowl.domain.category.entity.CategoryName;
import com.example.sendowl.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;

@SpringBootApplication
@RequiredArgsConstructor
public class SendOwlApplication implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    public static void main(String[] args) {
        SpringApplication.run(SendOwlApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // 카테고리 인서트하는 로직 적용 => 나중에 삭제
        for(CategoryName categoryName : CategoryName.values()){
            Optional<Category> optCategory = categoryRepository.findByCategoryName(categoryName);
            if(optCategory.isEmpty()){
                categoryRepository.save(
                        new Category().builder()
                                .categoryName(categoryName)
                                .build()
                );
            }
        }
    }
}

