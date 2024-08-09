package com.dev.kioki.domain.group.dto;

import lombok.*;

public class GroupRequestDTO {
    @Data
    public static class GroupMemberRequest {
        private Long userId;
    }
}
