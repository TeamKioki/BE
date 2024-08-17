package com.dev.kioki.domain.kiosk.converter;

import com.dev.kioki.domain.kiosk.dto.ModelRequestDTO;
import com.dev.kioki.domain.kiosk.dto.ModelResponseDTO;
import com.dev.kioki.domain.kiosk.entity.Model;
import com.dev.kioki.domain.store.dto.StoreResponseDTO;
import com.dev.kioki.domain.store.entity.Store;
import com.dev.kioki.domain.user.converter.UserConverter;
import com.dev.kioki.domain.user.dto.UserResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class ModelConverter {
    public static ModelResponseDTO.ModelDTO toDTO(Model model) {
        return ModelResponseDTO.ModelDTO.builder()
                .id(model.getId())
                .name(model.getName())
                .image_url(model.getImageUrl())
                .level((model.getLevel()))
                .rate((model.getRate()))
                .build();
    }

    public static List<ModelResponseDTO.ModelDTO> modelDTOList(List<Model> model) {

        return model.stream().map(ModelConverter::toDTO).toList();
    }

}
