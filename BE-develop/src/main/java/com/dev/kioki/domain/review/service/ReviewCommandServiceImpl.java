package com.dev.kioki.domain.review.service;

import com.dev.kioki.domain.review.converter.ReviewConverter;
import com.dev.kioki.domain.review.dto.ReviewRequestDTO;
import com.dev.kioki.domain.review.entity.Review;
import com.dev.kioki.domain.review.repository.BrandRepository;
import com.dev.kioki.domain.review.repository.ReviewRepository;
import com.dev.kioki.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewCommandServiceImpl implements ReviewCommandService {

    double sumScore = 0;

    private final ReviewRepository reviewRepository;

    private final UserRepository userRepository;

    private final BrandRepository brandRepository;

    @Override
    public Review addReview(ReviewRequestDTO.ReviewDTO request, Long userId, Long brandId){
        Review review = ReviewConverter.toReview(request);



    }
}
