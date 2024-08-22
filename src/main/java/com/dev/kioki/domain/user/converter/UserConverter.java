package com.dev.kioki.domain.user.converter;

import com.dev.kioki.domain.group.entity.GroupMember;
import com.dev.kioki.domain.inquire.entity.Inquire;
import com.dev.kioki.domain.kiosk.entity.Model;
import com.dev.kioki.domain.store.entity.Store;
import com.dev.kioki.domain.user.entity.Difficulty;
import com.dev.kioki.domain.user.entity.User;
import com.dev.kioki.domain.review.entity.Review;
import com.dev.kioki.domain.user.dto.UserResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.Set;
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

    public static UserResponseDTO.InquirePreViewDTO inquirePreViewDTO(Inquire inquire){
        return UserResponseDTO.InquirePreViewDTO.builder()
                .title(inquire.getTitle())
                .createdAt(inquire.getCreatedAt())
                .build();
    }

    public static UserResponseDTO.InquirePreViewListDTO inquirePreViewListDTO(Page<Inquire> inquireList) {

        List<UserResponseDTO.InquirePreViewDTO> inquirePreViewDTOList = inquireList.stream()
                .map(UserConverter::inquirePreViewDTO).collect(Collectors.toList());

        return UserResponseDTO.InquirePreViewListDTO.builder()
                .isLast(inquireList.isLast())
                .isFirst(inquireList.isFirst())
                .totalPage(inquireList.getTotalPages())
                .totalElements(inquireList.getTotalElements())
                .listSize(inquirePreViewDTOList.size())
                .inquireList(inquirePreViewDTOList)
                .build();
    }

    public static List<UserResponseDTO.UserGroupDTO> userGroupSearchDTO(List<User> users, List<GroupMember> groupMembers){

        Set<Long> groupMemberIds = groupMembers.stream()
                .map(GroupMember::getMemberUserId)
                .collect(Collectors.toSet());

        return users.stream().map(user -> {
            boolean isGroupMember = groupMemberIds.contains(user.getId());
            return UserResponseDTO.UserGroupDTO.builder()
                    .userId(user.getId())
                    .name(user.getName())
                    .phone(user.getPhone())
                    .isGroupMember(isGroupMember)
                    .introduction(user.getIntroduction())
                    .imageName(user.getImageName())
                    .build();
        }).collect(Collectors.toList());
    }

    public static UserResponseDTO.UserModelDTO userModelDTO(Model model){
        List<Long> store_ids = model.getStore().stream().map(Store::getId).toList();

        return UserResponseDTO.UserModelDTO.builder()
                .modelId(model.getId())
                .name(model.getName())
                .imageUrl(model.getImageUrl())
                .level(model.getLevel())
                .rate(model.getRate())
                .stores_id(store_ids)
                .build();
    }

    public static List<UserResponseDTO.UserModelDTO> userModelListDTO(List<Model> model) {

        return model.stream().map(UserConverter::userModelDTO).toList();
    }

    public static UserResponseDTO.UserDifficultyDTO toUserDifficultyDTO(Difficulty difficulty) {
        return UserResponseDTO.UserDifficultyDTO.builder()
                .reasonId(difficulty.getId())
                .reason(difficulty.getReason())
                .build();
    }

    public static List<UserResponseDTO.UserDifficultyDTO> toUserDifficultyDTOList(List<Difficulty> difficultyList) {
        return difficultyList.stream()
                .map(UserConverter::toUserDifficultyDTO)
                .toList();
    }
}
