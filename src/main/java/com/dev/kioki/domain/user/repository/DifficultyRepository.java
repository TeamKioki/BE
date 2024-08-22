package com.dev.kioki.domain.user.repository;

import com.dev.kioki.domain.user.entity.Difficulty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DifficultyRepository extends JpaRepository<Difficulty, Long> {
}
