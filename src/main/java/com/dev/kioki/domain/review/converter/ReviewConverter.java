package com.dev.kioki.domain.review.converter;

import com.dev.kioki.domain.review.dto.ReviewRequestDTO;
import com.dev.kioki.domain.review.dto.ReviewResponseDTO;
import com.dev.kioki.domain.review.entity.Review;

import java.time.LocalDateTime;

public class ReviewConverter {
    public static Review toReview(ReviewRequestDTO.ReviewDTO request){
        return Review.builder()
                .score(request.getScore())
                .advantage_content(request.getAdvantage_content())
                .advantages(request.getAdvantages())
                .disadvantage_content(request.getDisadvantage_content())
                .disadvantages(request.getDisadvantages())
                .build();
    }

    public static ReviewResponseDTO.CreateReviewResultDTO toCreateReviewResultDTO(Review review){
        return ReviewResponseDTO.CreateReviewResultDTO.builder()
                .reviewId(review.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
