package com.dev.kioki.domain.user.converter;

import com.dev.kioki.domain.user.entity.User;
import com.dev.kioki.domain.review.entity.Review;
import com.dev.kioki.domain.user.dto.UserResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserConverter {

    public static UserResponseDTO.UserInfoDTO userInfoDTO(Optional<User> userOptional) {
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return UserResponseDTO.UserInfoDTO.builder()
                    .name(user.getName())
                    .phone(user.getPhone())
                    .introduction(user.getIntroduction())
                    .imageName(user.getImageName())
                    .fontSize(user.getFontSize())
                    .fontColor(user.getFontColor())
                    .reviewList(user.getReviewList())
                    .helperList(user.getHelperList())
                    .build();
        } else {
            throw new IllegalArgumentException("유저를 찾을 수 없습니다.");
        }
    }
    public static UserResponseDTO.ReviewPreViewDTO reviewPreViewDTO(Review review){
        return UserResponseDTO.ReviewPreViewDTO.builder()
                .name(review.getUser().getName())
                .score(review.getScore())
                .createdAt(review.getCreatedAt())
                .content(review.getContent())
                .build();

    }

    public static UserResponseDTO.ReviewPreViewListDTO reviewPreViewListDTO(Page<Review> reviewList) {

        List<UserResponseDTO.ReviewPreViewDTO> reviewPreViewDTOList = reviewList.stream()
                .map(UserConverter::reviewPreViewDTO).collect(Collectors.toList());

        return UserResponseDTO.ReviewPreViewListDTO.builder()
                .isLast(reviewList.isLast())
                .isFirst(reviewList.isFirst())
                .totalPage(reviewList.getTotalPages())
                .totalElements(reviewList.getTotalElements())
                .listSize(reviewPreViewDTOList.size())
                .reviewList(reviewPreViewDTOList)
                .build();
    }
}
