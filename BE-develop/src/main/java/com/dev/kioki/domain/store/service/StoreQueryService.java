package com.dev.kioki.domain.store.service;

import com.dev.kioki.domain.store.dto.StoreRequestDTO;
import com.dev.kioki.domain.store.entity.Store;

import java.util.List;

public interface StoreQueryService {
    List<Store> findAll();

    List<Store> findAllByFilter(Integer level, Integer count, Float distance);

    Store createStore(StoreRequestDTO.StoreDTO storeRequestDTO);
}
