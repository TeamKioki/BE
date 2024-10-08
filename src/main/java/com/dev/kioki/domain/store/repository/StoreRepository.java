package com.dev.kioki.domain.store.repository;

import com.dev.kioki.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {
    @Query("SELECT s FROM Store s " +
            "WHERE (:level IS NULL OR s.model.level = :level) " +
            "AND (:kiosk_count IS NULL OR s.kioskCount = :kiosk_count) " +
            "AND (:distance IS NULL OR s.distance = :distance)")
    List<Store> findByFilter(Integer level, Integer kiosk_count, Float distance);
}
