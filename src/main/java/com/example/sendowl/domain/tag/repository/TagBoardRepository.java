package com.example.sendowl.domain.tag.repository;

import com.example.sendowl.domain.tag.entity.TagBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagBoardRepository extends JpaRepository<TagBoard, Long> {
}

