package com.dev.kioki.domain.review.service;

import com.dev.kioki.domain.kiosk.entity.Model;
import com.dev.kioki.domain.kiosk.repository.ModelRepository;
import com.dev.kioki.domain.review.entity.Review;
import com.dev.kioki.domain.review.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewQueryServiceImpl implements ReviewQueryService {

    private final ReviewRepository reviewRepository;

    private final ModelRepository modelRepository;

    @Override
    public Page<Review> getReviewList(Long modelId, Integer page) {
        Model model = modelRepository.findById(modelId).get();

        Page<Review> ModelPage = reviewRepository.findAllByModel(model, PageRequest.of(page, 10));
        return ModelPage;
    }

}
