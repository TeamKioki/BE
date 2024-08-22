package com.dev.kioki.domain.review.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class ReviewRequestDTO {
    @Getter
    public static class ReviewDTO{
        String advantage_content;

        String disadvantage_content;

        String advantages;

        String disadvantages;

        @NotNull
        Double score;
    }

}
