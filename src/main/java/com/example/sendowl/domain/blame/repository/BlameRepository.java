package com.example.sendowl.domain.blame.repository;

import com.example.sendowl.domain.blame.entity.Blame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BlameRepository extends JpaRepository<Blame, Long>, JpaSpecificationExecutor {
}

