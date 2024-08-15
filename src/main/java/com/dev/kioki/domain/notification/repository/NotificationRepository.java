package com.dev.kioki.domain.notification.repository;

import com.dev.kioki.domain.notification.entity.Notification;
import com.dev.kioki.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Optional<Notification> findByUser(User user);
}
