package com.dev.kioki.domain.review.controller;

import com.dev.kioki.domain.review.dto.ReviewRequestDTO;
import com.dev.kioki.domain.review.dto.ReviewResponseDTO;
import com.dev.kioki.domain.review.entity.Review;
import com.dev.kioki.domain.review.service.ReviewCommandService;
import com.dev.kioki.domain.store.dto.StoreRequestDTO;
import com.dev.kioki.domain.user.entity.User;
import com.dev.kioki.global.common.BaseResponse;
import com.dev.kioki.global.security.annotation.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewCommandService reviewCommandService;
    @PostMapping("/{brandId}/add")
    @Operation(summary = "키오스크 브랜드에 리뷰 추가",description = "리뷰를 추가합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공")
    })
    @Parameters({
            @Parameter(name = "brandId", description = "브랜드 아이디입니다.")
    })
    public BaseResponse<ReviewResponseDTO.CreateReviewResultDTO> createReview(@RequestBody @Valid ReviewRequestDTO.ReviewDTO request,
                                                                      @AuthUser User user,
                                                                      @PathVariable(name = "brandId") Long brandId){
        Review review = reviewCommandService.addReview(request, user.getId(), brandId);
        return BaseResponse.onSuccess(StoreConverter.StoreToDTO(store));
    }
}
