package com.dev.kioki.domain.store.controller;

import com.dev.kioki.domain.store.converter.StoreConverter;
import com.dev.kioki.domain.store.dto.StoreResponseDTO;
import com.dev.kioki.domain.store.entity.Store;
import com.dev.kioki.domain.store.repository.StoreRepository;
import com.dev.kioki.domain.store.service.StoreQueryService;
import com.dev.kioki.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/stores")
public class StoreController {

    private final StoreRepository storeRepository;
    private final StoreQueryService storeQueryService;

    @GetMapping()
    @Operation(summary = "음식점 가게 목록", description = "가게 목록을 모두 불러옵니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public BaseResponse<List<StoreResponseDTO.StoreDTO>> getStores() {
        List<Store> store = storeQueryService.findAll();
        return BaseResponse.onSuccess(StoreConverter.storeDTO(store));
    }


}
