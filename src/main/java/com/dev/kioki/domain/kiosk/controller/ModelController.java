package com.dev.kioki.domain.kiosk.controller;

import com.dev.kioki.domain.kiosk.converter.ModelConverter;
import com.dev.kioki.domain.kiosk.dto.ModelRequestDTO;
import com.dev.kioki.domain.kiosk.dto.ModelResponseDTO;
import com.dev.kioki.domain.kiosk.entity.Model;
import com.dev.kioki.domain.kiosk.repository.ModelRepository;
import com.dev.kioki.domain.kiosk.service.ModelQueryService;
import com.dev.kioki.domain.store.converter.StoreConverter;
import com.dev.kioki.domain.store.dto.StoreRequestDTO;
import com.dev.kioki.domain.store.dto.StoreResponseDTO;
import com.dev.kioki.domain.store.entity.Store;
import com.dev.kioki.domain.store.repository.StoreRepository;
import com.dev.kioki.domain.store.service.StoreQueryService;
import com.dev.kioki.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/model")
@Tag(name = "키오스크 모델 관련 컨트롤러")
public class ModelController {

    private final ModelRepository modelRepository;
    private final ModelQueryService modelQueryService;

    @PostMapping
    @Operation(summary = "(백엔드용) 키오스크 모델 등록", description="키오스크 모델을 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public BaseResponse<ModelResponseDTO.ModelDTO> createStore(@RequestBody ModelRequestDTO.ModelDTO modelRequestDTO) {
        Model model = modelQueryService.createModel(modelRequestDTO);
        return BaseResponse.onSuccess(ModelConverter.toDTO(model));
    }

    @GetMapping
    @Operation(summary = "키오스크 모델 목록", description = "서버에 등록된 키오스크 모델을 불러옵니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "COMMON200", description="OK, 성공"),
    })
    public BaseResponse<List<ModelResponseDTO.ModelDTO>> getAllModels() {
        List<Model> models = modelRepository.findAll();
        return BaseResponse.onSuccess(ModelConverter.modelDTOList(models));
    }
}
