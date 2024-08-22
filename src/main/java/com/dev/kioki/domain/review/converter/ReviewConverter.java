package com.dev.kioki.domain.review.converter;

import org.springframework.data.domain.Page;
import com.dev.kioki.domain.review.dto.ReviewRequestDTO;
import com.dev.kioki.domain.review.dto.ReviewResponseDTO;
import com.dev.kioki.domain.review.entity.Review;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public static ReviewResponseDTO.ReviewPreViewDTO reviewPreViewDTO(Review review){
        return ReviewResponseDTO.ReviewPreViewDTO.builder()
                .ownerName(review.getUser().getName())
                .score(review.getScore())
                .advantage_content(review.getAdvantage_content())
                .advantages(review.getAdvantages())
                .disadvantage_content(review.getDisadvantage_content())
                .disadvantages(review.getDisadvantages())
                .createdAt(review.getCreatedAt().toLocalDate())
                .build();
    }
    public static ReviewResponseDTO.ReviewPreViewListDTO reviewPreViewListDTO(Page<Review> reviewPage) {
        List<ReviewResponseDTO.ReviewPreViewDTO> reviewPreViewDTOList = reviewPage.stream()
                .map(ReviewConverter::reviewPreViewDTO).collect(Collectors.toList());
        return ReviewResponseDTO.ReviewPreViewListDTO.builder()
                .isLast(reviewPage.isLast())
                .isFirst(reviewPage.isFirst())
                .totalPage(reviewPage.getTotalPages())
                .totalElements(reviewPage.getTotalElements())
                .listSize(reviewPreViewDTOList.size())
                .reviewList(reviewPreViewDTOList)
                .build();
    }
}
