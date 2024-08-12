package com.dev.kioki.domain.store.controller;

import com.dev.kioki.domain.store.converter.StoreConverter;
import com.dev.kioki.domain.store.dto.StoreResponseDTO;
import com.dev.kioki.domain.store.entity.Store;
import com.dev.kioki.domain.store.repository.StoreRepository;
import com.dev.kioki.domain.store.service.StoreQueryService;
import com.dev.kioki.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/stores")
@Tag(name = "음식점 가게 관련 컨트롤러")
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

    @GetMapping("/filter")
    @Operation(summary = "음식점 가게 필터 목록", description = "필러 적용된 가게 목록을 모두 불러옵니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    @Parameters({
            @Parameter(name = "level", description = "키오스크 난이도"),
            @Parameter(name = "store_count", description = "매장 내 키오스크 개수"),
            @Parameter(name = "distance", description = "거리"),
    })
    public BaseResponse<List<StoreResponseDTO.StoreDTO>> getStoresByFilter(
            @RequestParam(name = "level", required = true) Integer level,
            @RequestParam(name = "store_count", required = true) Integer store_count,
            @RequestParam(name = "distance", required = true) Float distance) {

        List<Store> stores = storeQueryService.findAllByFilter(level,store_count,distance);
        return BaseResponse.onSuccess(StoreConverter.storeDTO(stores));
    }

}
