package com.example.sendowl.domain.category.repository;

import com.example.sendowl.domain.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);

    Optional<Category> existsByName(String name);
}

