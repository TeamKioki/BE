package com.dev.kioki.domain.store.dto;

import com.dev.kioki.domain.brand.entity.enums.BrandType;
import com.dev.kioki.domain.kiosk.entity.Model;
import lombok.*;

public class StoreResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StoreDTO {
        private Long id;

        private Model model;

        private String name;

        private BrandType brand;

        private Integer store_count;

        private Integer kiosk_count;

        private String icon_url;
    }
}
