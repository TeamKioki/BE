package com.dev.kioki.domain.inquire.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class InquireResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateInqireResultDTO {
        Long inquire_id;
        LocalDateTime createdAt;
    }


}
