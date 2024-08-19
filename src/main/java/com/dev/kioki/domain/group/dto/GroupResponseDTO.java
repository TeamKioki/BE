package com.dev.kioki.domain.group.dto;

import com.dev.kioki.domain.user.entity.enums.FontSize;
import lombok.*;

import java.util.List;

public class GroupResponseDTO {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class GroupMemberDTO {
        private Long memberId;
        private Long userId;
        private String nickname;
        private String profileName;
        private String noteTitle;
        private String noteText;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GroupMemberListDTO{
        List<GroupMemberDTO> groupMemberList;
        Integer listSize;
        Integer totalPage;
        Long totalElements;
        Boolean isFirst;
        Boolean isLast;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class GroupMemberDetailsDTO {
        private Long memberId;
        private Long userId;
        private String profileName;
        private String noteTitle;
        private String noteText;
        private String color;
        private FontSize fontSize;
        private String nickname;
    }
}
