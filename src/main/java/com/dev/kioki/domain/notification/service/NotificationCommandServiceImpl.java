package com.dev.kioki.domain.notification.service;

import com.dev.kioki.domain.notification.converter.NotificationConverter;
import com.dev.kioki.domain.notification.dto.NotificationRequestDTO;
import com.dev.kioki.domain.notification.entity.Notification;
import com.dev.kioki.domain.notification.repository.NotificationRepository;
import com.dev.kioki.domain.notification.service.NotificationCommandService;
import com.dev.kioki.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class NotificationCommandServiceImpl implements NotificationCommandService {

    private final NotificationRepository notificationRepository;

    public NotificationCommandServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Notification updateNotification(Long user_id, NotificationRequestDTO.NotificationDTO request) {
        Notification notification = NotificationConverter.toNotification(request);

        // 알림 설정 필드를 개별적으로 설정
        notification.setAppAlert(request.getAppAlert());
        notification.setQuestionAlert(request.getQuestionAlert());
        notification.setMarketingAlert(request.getMarketingAlert());
        notification.setAnnouncementAlert(request.getAnnouncementAlert());
        notification.setEmailAlert(request.getEmailAlert());

        return notificationRepository.save(notification);

    }



}
