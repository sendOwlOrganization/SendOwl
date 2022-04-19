package com.example.sendowl.domain.category.repository;


import com.example.sendowl.domain.board.entity.Board;
import com.example.sendowl.domain.category.entity.Category;
import com.example.sendowl.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query(value = "SELECT c FROM Category c WHERE c.categoryName=:categoryName")
    Optional<Category> findByCategoryName(String categoryName);
}

