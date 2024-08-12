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
                .store_count(store.getStore_count())
                .kiosk_count(store.getKiosk_count())
                .icon_url(store.getIcon_url())
                .build();
    }

}
