package com.dev.kioki.domain.notification.converter;

import com.dev.kioki.domain.inquire.dto.InquireRequestDTO;
import com.dev.kioki.domain.inquire.dto.InquireResponseDTO;
import com.dev.kioki.domain.inquire.entity.Inquire;
import com.dev.kioki.domain.notification.dto.NotificationRequestDTO;
import com.dev.kioki.domain.notification.dto.NotificationResponseDTO;
import com.dev.kioki.domain.notification.entity.Notification;

public class NotificationConverter {

    public static Notification toNotification(NotificationRequestDTO.NotificationDTO request) {
        return Notification.builder()
                .title(request.getTitle())
                .body(request.getBody())
                .build();
    }
    public static NotificationResponseDTO.UpdateNotificationResultDTO toUpdateNotificationResultDTO(Notification notification) {
        return NotificationResponseDTO.UpdateNotificationResultDTO.builder()
                .notification_id(notification.getId())
                .createdAt(notification.getCreatedAt())
                .build();
    }
}
