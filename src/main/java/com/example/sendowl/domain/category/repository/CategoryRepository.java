package com.example.sendowl.domain.category.repository;

import com.example.sendowl.domain.category.dto.CategoryCount;
import com.example.sendowl.domain.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);

//    @Query(value =
//            "select * " +
//                    "from category as C " +
//                    "left join " +
//                    "(select board.category_id, count(*) as count from board group by board.category_id) as B " +
//                    "on C.category_id = B.category_id " +
//                    "order by count desc;"
//    )
    @Query(value = "select new com.example.sendowl.domain.category.dto.CategoryCount(C.id, C.name, count(B))" +
            "from Category C left outer join Board B on C = B.category group by C order by count(B) desc "
    )
    List<CategoryCount> findCategoriesWithCount();
    boolean existsByName(String name);
}

