package com.dev.kioki.domain.group.dto;

import com.dev.kioki.domain.user.entity.enums.FontSize;
import lombok.*;

public class GroupRequestDTO {
    @Data
    public static class GroupMemberRequest {
        private Long userId;
    }

    @Data
    public static class GroupMemberUpdateDTO {

        private String noteTitle;

        private String noteBody;

        private String color;

        private FontSize fontSize;

        private String nickname;

        private String profileName;
    }
}
