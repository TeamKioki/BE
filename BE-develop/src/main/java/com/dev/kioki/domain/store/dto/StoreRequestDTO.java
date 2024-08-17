package com.dev.kioki.domain.store.dto;

import com.dev.kioki.domain.brand.entity.enums.BrandType;
import lombok.*;

public class StoreRequestDTO {

    @Getter
    @Setter
    public static class StoreDTO {
        private Long modelId;

        private Integer level;

        private Float rate;

        private Float distance;

        private String name;

        private BrandType brand;

        private Integer store_count;

        private Integer kiosk_count;

        private String icon_url;
    }
}
