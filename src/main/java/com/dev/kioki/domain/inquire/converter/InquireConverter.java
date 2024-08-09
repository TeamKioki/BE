package com.dev.kioki.domain.inquire.converter;

import com.dev.kioki.domain.inquire.dto.InquireRequestDTO;
import com.dev.kioki.domain.inquire.dto.InquireResponseDTO;
import com.dev.kioki.domain.inquire.entity.Inquire;

public class InquireConverter {

    public static Inquire toInquire(InquireRequestDTO.InquireDTO request) {
        return Inquire.builder()
                .title(request.getTitle())
                .body(request.getBody())
                .imageUrl(request.getImageUrl())
                .build();
    }

    public static InquireResponseDTO.CreateInqireResultDTO toCreateInquireResultDTO(Inquire inquire) {
        return InquireResponseDTO.CreateInqireResultDTO.builder()
                .inquire_id(inquire.getId())
                .createdAt(inquire.getCreatedAt())
                .build();
    }
}
