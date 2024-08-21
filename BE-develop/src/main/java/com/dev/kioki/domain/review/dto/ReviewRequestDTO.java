package com.dev.kioki.domain.review.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class ReviewRequestDTO {
    @Getter
    public static class ReviewDTO{
        String content;

        String advantages;

        String disadvantages;

        Double score;
    }

}
