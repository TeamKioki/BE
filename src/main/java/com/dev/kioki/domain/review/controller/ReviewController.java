package com.dev.kioki.domain.review.controller;

import com.dev.kioki.domain.review.converter.ReviewConverter;
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
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/reviews")
@Tag(name = "리뷰 관련 컨트롤러")
public class ReviewController {
    private final ReviewCommandService reviewCommandService;
    @PostMapping("/{modelId}/add")
    @Operation(summary = "모델에 리뷰 추가",description = "리뷰를 추가합니다. score는 double형으로 주세요")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공")
    })
    @Parameters({
            @Parameter(name = "modelId", description = "모델 아이디입니다.")
    })
    public BaseResponse<ReviewResponseDTO.CreateReviewResultDTO> createReview(@RequestBody @Valid ReviewRequestDTO.ReviewDTO request,
                                                                      @AuthUser User user,
                                                                      @PathVariable(name = "modelId") Long modelId){
        Review review = reviewCommandService.addReview(request, user.getId(), modelId);
        return BaseResponse.onSuccess(ReviewConverter.toCreateReviewResultDTO(review));
    }
}
