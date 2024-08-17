package com.dev.kioki.domain.store.service;

import com.dev.kioki.domain.kiosk.entity.Model;
import com.dev.kioki.domain.kiosk.repository.ModelRepository;
import com.dev.kioki.domain.store.dto.StoreRequestDTO;
import com.dev.kioki.domain.store.entity.Store;
import com.dev.kioki.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreQueryServiceImpl implements StoreQueryService {

    private final StoreRepository storeRepository;
    private final ModelRepository modelRepository;

    @Override
    public List<Store> findAll() {
        return storeRepository.findAll();
    }

    @Override
    public List<Store> findAllByFilter(Integer level, Integer kiosk_count, Float distance) {
        return storeRepository.findByFilter(level,kiosk_count,distance);
    }

    @Override
    public Store createStore(StoreRequestDTO.StoreDTO storeRequestDTO) {
        Model model = modelRepository.findById(storeRequestDTO.getModelId()).orElse(null);

        Store store = Store.builder()
                .model(model)
                .name(storeRequestDTO.getName())
                .brand(storeRequestDTO.getBrand())
                .storeCount(storeRequestDTO.getStore_count())
                .kioskCount(storeRequestDTO.getKiosk_count())
                .distance(storeRequestDTO.getDistance())
                .icon_url(storeRequestDTO.getIcon_url())
                .build();

        return storeRepository.save(store);
    }
}
