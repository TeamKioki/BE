package com.dev.kioki.domain.review.controller;

import com.dev.kioki.domain.review.converter.ReviewConverter;
import com.dev.kioki.domain.review.dto.ReviewRequestDTO;
import com.dev.kioki.domain.review.dto.ReviewResponseDTO;
import com.dev.kioki.domain.review.entity.Review;
import com.dev.kioki.domain.review.service.ReviewCommandService;
import com.dev.kioki.domain.review.service.ReviewQueryService;
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
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/reviews")
@Tag(name = "리뷰 관련 컨트롤러")
public class ReviewController {
    private final ReviewCommandService reviewCommandService;
    private final ReviewQueryService reviewQueryService;
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

    @GetMapping("/{modelId}/select")
    @Operation(summary = "특정 모델의 리뷰 목록 조회 API",description = "특정 모델 리뷰들의 목록을 조회하는 API입니다. query String 으로 page 번호를 주세요")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공")
    })
    @Parameters({
            @Parameter(name = "modelId", description = "모델 아이디입니다."),
            @Parameter(name = "page", description = "페이지 번호, 0번이 1 페이지 입니다."),
    })
    public BaseResponse<ReviewResponseDTO.ReviewPreViewListDTO> getReviewList(@PathVariable(name = "modelId") Long modelId, @RequestParam(name = "page") Integer page){
        Page<Review> reviewPage = reviewQueryService.getReviewList(modelId, page);
        return BaseResponse.onSuccess(ReviewConverter.reviewPreViewListDTO(reviewPage));
    }
}
