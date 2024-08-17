package com.dev.kioki.domain.notification.service;

import com.dev.kioki.domain.notification.dto.NotificationRequestDTO;
import com.dev.kioki.domain.notification.entity.Notification;

public interface NotificationCommandService {

    Notification updateNotification(Long user_id, NotificationRequestDTO.NotificationDTO request);

}
