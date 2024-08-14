package com.dev.kioki.domain.kiosk.dto;

import com.dev.kioki.domain.brand.entity.enums.BrandType;
import lombok.Getter;
import lombok.Setter;

public class ModelRequestDTO {

    @Getter
    @Setter
    public static class ModelDTO {
        private Long id;

        private String name;

        private String image_url;

        private Integer level;

        private Float rate;
    }
}
