package com.dev.kioki.domain.notification.entity;

import com.dev.kioki.domain.user.entity.User;
import com.dev.kioki.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@DynamicInsert
@DynamicUpdate
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 알림 설정 관련 필드 추가
    private Boolean appAlert;          // 앱 실행 중 알림 받기
    private Boolean questionAlert;     // 문의사항 답변 알림
    private Boolean marketingAlert;    // 혜택, 마케팅 알림
    private Boolean announcementAlert; // 공지사항 알림
    private Boolean emailAlert;        // 이메일 알림

    public void setAppAlert(Boolean appAlert) {
        this.appAlert = appAlert;
    }

    public void setQuestionAlert(Boolean questionAlert) {
        this.questionAlert = questionAlert;
    }

    public void setMarketingAlert(Boolean marketingAlert) {
        this.marketingAlert = marketingAlert;
    }

    public void setAnnouncementAlert(Boolean announcementAlert) {
        this.announcementAlert = announcementAlert;
    }

    public void setEmailAlert(Boolean emailAlert) {
        this.emailAlert = emailAlert;
    }
}
