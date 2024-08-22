package com.dev.kioki.domain.brand.repository;

import com.dev.kioki.domain.brand.entity.Brand;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    @Query("SELECT AVG(avgScore) FROM Brand WHERE id = :brandId")
    Double avgScore(@Param("brandId") Long brandId);
}
