package com.dev.kioki.domain.review.converter;

import com.dev.kioki.domain.review.dto.ReviewRequestDTO;
import com.dev.kioki.domain.review.entity.Review;

public class ReviewConverter {
    public static Review toReview(ReviewRequestDTO.ReviewDTO request){
        return Review.builder()
                .score()
    }
}
