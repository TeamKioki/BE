package com.dev.kioki.domain.store.repository;

import com.dev.kioki.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
