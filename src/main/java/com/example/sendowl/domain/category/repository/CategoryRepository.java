package com.example.sendowl.domain.category.repository;

import com.example.sendowl.domain.category.dto.CategoryCount;
import com.example.sendowl.domain.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);

    @Query(value = "select new com.example.sendowl.domain.category.dto.CategoryCount(C.id, C.name, count(B))" +
            "from Category C left outer join Board B on C = B.category group by C order by count(B) desc "
    )
    List<CategoryCount> findCategoriesWithBoardCount();

    boolean existsByName(String name);
}

