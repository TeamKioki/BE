package com.dev.kioki.domain.review.service;

import com.dev.kioki.domain.brand.entity.Brand;
import com.dev.kioki.domain.review.Handler.ReviewHandler;
import com.dev.kioki.domain.review.converter.ReviewConverter;
import com.dev.kioki.domain.review.dto.ReviewRequestDTO;
import com.dev.kioki.domain.review.entity.Review;
import com.dev.kioki.domain.review.repository.BrandRepository;
import com.dev.kioki.domain.review.repository.ReviewRepository;
import com.dev.kioki.domain.user.repository.UserRepository;
import com.dev.kioki.global.common.code.status.ErrorStatus;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewCommandServiceImpl implements ReviewCommandService {

    private final ReviewRepository reviewRepository;

    private final UserRepository userRepository;

    private final BrandRepository brandRepository;

    @Override
    public Review addReview(ReviewRequestDTO.ReviewDTO request, Long userId, Long brandId){

        //장점, 단점 선택/작성되지 않았을 경우 에러 처리
        if(request.getAdvantage_content() == null && request.getAdvantages() == null)
            throw new ReviewHandler(ErrorStatus.ADVANTAGE_CONTENT_NOT_FOUND);
        if(request.getDisadvantages() == null && request.getDisadvantage_content() == null)
            throw new ReviewHandler(ErrorStatus.DISADVANTAGE_CONTENT_NOT_FOUND);

        //리뷰 생성
        Review review = ReviewConverter.toReview(request);

        //id 매핑
        review.setUser(userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found")));
        review.setBrand(brandRepository.findById(brandId).orElseThrow(() -> new EntityNotFoundException("Brand not found")));

        reviewRepository.save(review);

        //브랜드 평균 점수 갱신

        Brand brand = review.getBrand();
        brand.setAvgScore(brandRepository.avgScore(brandId));
        brandRepository.save(brand);

        return review;
    }
}
