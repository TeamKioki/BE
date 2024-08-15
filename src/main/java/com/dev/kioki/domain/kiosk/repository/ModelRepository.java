package com.dev.kioki.domain.kiosk.repository;

import com.dev.kioki.domain.kiosk.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModelRepository extends JpaRepository<Model, Long > {
    List<Model> findByUserId(Long userId);
}
