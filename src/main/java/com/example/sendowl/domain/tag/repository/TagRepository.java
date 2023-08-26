package com.example.sendowl.domain.tag.repository;

import com.example.sendowl.domain.tag.dto.TagCount;
import com.example.sendowl.domain.tag.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String name);

    @Query(value = "select new com.example.sendowl.domain.tag.dto.TagCount(C.id, C.name, count(B))" +
            "from Tag C left outer join Board B on C = B.tag group by C order by count(B) desc "
    )
    List<TagCount> findTagsWithBoardCount();

    boolean existsByName(String name);
}

