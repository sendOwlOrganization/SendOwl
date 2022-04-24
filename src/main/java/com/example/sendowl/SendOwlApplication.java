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
public class SendOwlApplication implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    public static void main(String[] args) {
        SpringApplication.run(SendOwlApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // 카테고리 인서트하는 로직 적용 => 나중에 삭제
        List<Category> listCategory = categoryRepository.findAll();
        Map<String,Boolean> tbCategoryName = new HashMap<String,Boolean>();

        listCategory.stream().forEach(category -> tbCategoryName.put(category.getCategoryName().getKey(), true));

        List<Category> emptyCategory = new ArrayList<>();
        for(CategoryName categoryName : CategoryName.values()){
            if(!tbCategoryName.containsKey(categoryName.getKey())){
                emptyCategory.add(new Category().builder().categoryName(categoryName).build());
            }
        }

        if(emptyCategory.size() != 0){
            categoryRepository.saveAll(emptyCategory);
        }
    }
}

