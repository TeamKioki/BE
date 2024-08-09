package com.dev.kioki.domain.user.dto;

import com.dev.kioki.domain.user.entity.enums.FontSize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class UserRequestDTO {

    @Getter
    public static class ReviewDto {
        @NotBlank
        Long user_id;
        @NotNull
        private Double score;
        @NotNull
        private String content;

    }

    @Getter
    public static class InquireDto {
        @NotBlank
        Long user_id;
        @NotNull
        private String title;
        @NotNull
        private String body;
        @NotNull
        private String imageUrl;
    }
}
