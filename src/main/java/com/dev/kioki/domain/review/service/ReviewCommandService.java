package com.dev.kioki.domain.review.service;

import com.dev.kioki.domain.review.dto.ReviewRequestDTO;
import com.dev.kioki.domain.review.entity.Review;

public interface ReviewCommandService {
    Review addReview(ReviewRequestDTO.ReviewDTO request, Long userId, Long brandId);
}
