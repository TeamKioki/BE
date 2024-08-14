package com.dev.kioki.domain.kiosk.service;

import com.dev.kioki.domain.kiosk.dto.ModelRequestDTO;
import com.dev.kioki.domain.kiosk.entity.Model;
import com.dev.kioki.domain.store.dto.StoreRequestDTO;
import com.dev.kioki.domain.store.entity.Store;

public interface ModelQueryService {
    Model createModel(ModelRequestDTO.ModelDTO modelRequestDTO);
}
