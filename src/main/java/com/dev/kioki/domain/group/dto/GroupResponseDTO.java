package com.dev.kioki.domain.group.dto;

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
        private String profilePictureUrl;
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
}
