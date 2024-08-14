package com.dev.kioki.domain.user.dto;

import com.dev.kioki.domain.review.entity.Review;
import com.dev.kioki.domain.user.entity.Helper;
import com.dev.kioki.domain.user.entity.enums.FontSize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class UserResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserInfoDTO {
        String name;
        String phone;
        String introduction;
        String imageName;
        FontSize fontSize;
        String fontColor;
        List<Review> reviewList;
        List<Helper> helperList;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewPreViewDTO {
        String name;
        Double score;
        String content;
        LocalDateTime createdAt;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewPreViewListDTO {
        List<ReviewPreViewDTO> reviewList;
        Integer listSize;
        Integer totalPage;
        Long totalElements;
        Boolean isFirst;
        Boolean isLast;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InquirePreViewDTO {
        String title;
        LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InquirePreViewListDTO {
        List<InquirePreViewDTO> inquireList;
        Integer listSize;
        Integer totalPage;
        Long totalElements;
        Boolean isFirst;
        Boolean isLast;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserGroupDTO {
        Long userId;
        String name;
        String phone;
        String introduction;
        String imageName;
        Boolean isGroupMember;
    }
}
