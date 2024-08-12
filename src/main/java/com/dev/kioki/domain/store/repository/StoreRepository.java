package com.dev.kioki.domain.store.repository;

import com.dev.kioki.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {
    List<Store> findByModel_LevelAndStoreCountAndDistance(Integer level, Integer store_count, Float distance);
}
