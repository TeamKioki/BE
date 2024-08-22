package com.dev.kioki.domain.review.service;

import com.dev.kioki.domain.review.entity.Review;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ReviewQueryService {
    Page<Review> getReviewList(Long modelId, Integer page);
}
