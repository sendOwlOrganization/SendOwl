package com.example.sendowl.domain.blame.repository;

import com.example.sendowl.domain.blame.BlameType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BlameTypeRepository extends JpaRepository<BlameType, Long>, JpaSpecificationExecutor{
    Boolean existsAllByName(String name);
}
