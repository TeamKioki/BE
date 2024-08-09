package com.dev.kioki.domain.inquire.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class InquireRequestDTO {

    @Getter
    public static class InquireDTO {
        @NotBlank
        String title;
        @NotNull
        String body;
        @NotNull
        String imageUrl;
    }
}
