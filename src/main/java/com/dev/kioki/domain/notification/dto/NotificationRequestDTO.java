package com.dev.kioki.domain.notification.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class NotificationRequestDTO {

    @Getter
    public static class NotificationDTO {
        @NotBlank
        String title;
        @NotNull
        String body;

        @NotNull
        Boolean appAlert;          // 앱 실행 중 알림 받기
        @NotNull
        Boolean questionAlert;     // 문의사항 답변 알림
        @NotNull
        Boolean marketingAlert;    // 혜택, 마케팅 알림
        @NotNull
        Boolean announcementAlert; // 공지사항 알림
        @NotNull
        Boolean emailAlert;        // 이메일 알림
    }
}
