package com.dev.kioki.domain.kiosk.service;

import com.dev.kioki.domain.kiosk.dto.ModelRequestDTO;
import com.dev.kioki.domain.kiosk.entity.Model;
import com.dev.kioki.domain.kiosk.repository.ModelRepository;
import com.dev.kioki.domain.store.dto.StoreRequestDTO;
import com.dev.kioki.domain.store.entity.Store;
import com.dev.kioki.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModelQueryServiceImpl implements ModelQueryService {

    private final ModelRepository modelRepository;

    @Override
    public Model createModel(ModelRequestDTO.ModelDTO modelRequestDTO) {

        Model model = Model.builder()
                .id(modelRequestDTO.getId())
                .name(modelRequestDTO.getName())
                .imageUrl(modelRequestDTO.getImage_url())
                .level(modelRequestDTO.getLevel())
                .rate(modelRequestDTO.getRate())
                .build();

        return modelRepository.save(model);
    }
}
