package com.dev.kioki.domain.auth.converter;

import com.dev.kioki.domain.auth.dto.AuthDTO.AuthResponse.*;
import com.dev.kioki.domain.auth.dto.AuthDTO.AuthRequest.*;
import com.dev.kioki.domain.user.entity.User;
import com.dev.kioki.domain.user.entity.enums.UserRole;

import java.time.LocalDateTime;

public class AuthConverter {

    public static SmsVerificationCodeResponse toSmsVerificationCodeResponse(String phone, String code) {
        return SmsVerificationCodeResponse.builder()
                .phone(phone)
                .code(code)
                .build();
    }

    public static SmsVerificationResultResponse toSmsVerificationResultResponse(String phone, Boolean isCodeValid) {
        return SmsVerificationResultResponse.builder()
                .phone(phone)
                .isCodeValid(isCodeValid)
                .build();
    }

    public static TokenResponse toTokenResponse(String accessToken, String refreshToken) {
        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public static User toUser(JoinRequest request, String imageUrl) {
        return User.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .birthday(request.getBirthday())
                .introduction(request.getIntroduction())
                .kioskDifficulty(request.getKioskDifficulty())
                .imageUrl(imageUrl)
                .userRole(UserRole.ROLE_USER)
                .build();
    }

    public static QuitResponse toQuit() {
        return QuitResponse.builder()
                .quitAt(LocalDateTime.now())
                .build();
    }
}
