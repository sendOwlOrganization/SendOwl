package com.example.sendowl.domain.blame.repository;

import com.example.sendowl.domain.blame.entity.BlameType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface BlameTypeRepository extends JpaRepository<BlameType, Long>, JpaSpecificationExecutor {
    Boolean existsAllByName(String name);

    List<BlameType> findAllByIsDeletedFalse();

    Optional<BlameType> findById(Long id);
}
