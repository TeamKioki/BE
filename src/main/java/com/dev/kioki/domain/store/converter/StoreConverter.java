package com.dev.kioki.domain.store.converter;

import com.dev.kioki.domain.store.dto.StoreResponseDTO;
import com.dev.kioki.domain.store.entity.Store;

import java.util.List;
import java.util.stream.Collectors;

public class StoreConverter {

    public static List<StoreResponseDTO.StoreDTO> storeDTO(List<Store> stores) {
        return stores.stream().map(StoreConverter::toDTO).collect(Collectors.toList());
    }

    public static StoreResponseDTO.StoreDTO toDTO(Store store) {
        return StoreResponseDTO.StoreDTO.builder()
                .id(store.getId())
                .name(store.getName())
                .brand(store.getBrand())
                .modelId(store.getModel().getId())
                .modelName(store.getModel().getName())
                .level(store.getModel().getLevel())
                .rate(store.getModel().getRate())
                .distance(store.getDistance())
                .store_count(store.getStoreCount())
                .kiosk_count(store.getKioskCount())
                .icon_url(store.getIcon_url())
                .build();
    }

}
