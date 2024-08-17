package com.dev.kioki.domain.kiosk.dto;

import com.dev.kioki.domain.brand.entity.enums.BrandType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ModelResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ModelDTO {
        private Long id;

        private String name;

        private String image_url;

        private Integer level;

        private Float rate;
    }
}
