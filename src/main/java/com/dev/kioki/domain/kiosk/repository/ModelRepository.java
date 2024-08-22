package com.dev.kioki.domain.kiosk.repository;

import com.dev.kioki.domain.kiosk.entity.Model;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ModelRepository extends JpaRepository<Model, Long > {
    @Query("SELECT u.modelList FROM User u WHERE u.id = :user_id")
    List<Model> findModelsByUserId(@Param("user_id") Long user_id);

    @Query("SELECT AVG(rate) FROM Model WHERE id = :modelId")
    Double avgRate(@Param("modelId") Long modelId);
}
