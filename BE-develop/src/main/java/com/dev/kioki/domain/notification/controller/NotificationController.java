package com.dev.kioki.domain.notification.controller;

import com.dev.kioki.domain.notification.converter.NotificationConverter;
import com.dev.kioki.domain.notification.dto.NotificationRequestDTO;
import com.dev.kioki.domain.notification.dto.NotificationResponseDTO;
import com.dev.kioki.domain.notification.entity.Notification;
import com.dev.kioki.domain.notification.service.NotificationCommandService;
import com.dev.kioki.domain.user.entity.User;
import com.dev.kioki.domain.user.service.UserQueryService;
import com.dev.kioki.global.common.BaseResponse;
import com.dev.kioki.global.security.annotation.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/notifications")
@Tag(name = "알림 관련 컨트롤러")
public class NotificationController {

    private final NotificationCommandService notificationCommandService;
    private final UserQueryService userQueryService;

    @PatchMapping("/settings")
    @Operation(summary = "알림 설정 업데이트 API", description = "알림 설정을 업데이트하는 API입니다.")
    public BaseResponse<NotificationResponseDTO.UpdateNotificationResultDTO> updateNotification(@RequestBody @Valid NotificationRequestDTO.NotificationDTO request,
                                                                                                @AuthUser User user) {
        Notification notification = notificationCommandService.updateNotification(user.getId(), request);
        return BaseResponse.onSuccess(NotificationConverter.toUpdateNotificationResultDTO(notification));
    }
}