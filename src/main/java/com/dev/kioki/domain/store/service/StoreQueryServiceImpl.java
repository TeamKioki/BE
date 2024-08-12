package com.dev.kioki.domain.store.service;

import com.dev.kioki.domain.store.entity.Store;
import com.dev.kioki.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreQueryServiceImpl implements StoreQueryService {

    private final StoreRepository storeRepository;

    @Override
    public List<Store> findAll() {
        return storeRepository.findAll();
    }

    @Override
    public List<Store> findAllByFilter(Integer level, Integer store_count, Float distance) {
        return storeRepository.findByModel_LevelAndStoreCountAndDistance(level,store_count,distance);
    }
}
