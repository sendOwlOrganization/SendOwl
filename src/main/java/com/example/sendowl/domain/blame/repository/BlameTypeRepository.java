package com.example.sendowl.domain.blame.repository;

import com.example.sendowl.domain.blame.BlameType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface BlameTypeRepository extends JpaRepository<BlameType, Long>, JpaSpecificationExecutor{
    Boolean existsAllByName(String name);

    Optional<BlameType> findById(Long id);
}
