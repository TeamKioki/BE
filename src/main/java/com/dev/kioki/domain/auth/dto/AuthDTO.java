package com.dev.kioki.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

public class AuthDTO {

    public static class AuthRequest {

        @Getter
        public static class JoinRequest {
            @NotBlank String name;
            @NotBlank String phone;
            @NotBlank String password;
            String imageName;
            @NotNull int age;
            String introduction;
            @NotBlank String kioskDifficulty;
        }

        @Getter
        public static class LoginRequest {
            @NotBlank String phone;
            @NotBlank String passsword;
        }

        @Getter
        public static class SmsSendRequest {
            @NotBlank String phone;
        }

        @Getter
        public static class SmsVerificationRequest {
            @NotBlank String phone;
            @NotBlank String code;
        }
    }

    public static class AuthResponse {

        @Getter
        @Builder
        @AllArgsConstructor(access = AccessLevel.PROTECTED)
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        public static class TokenResponse {
            String accessToken;
            String refreshToken;
        }

        @Getter
        @Builder
        @AllArgsConstructor(access = AccessLevel.PROTECTED)
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        public static class SmsVerificationCodeResponse {
            String phone;
            String code;
        }

        @Getter
        @Builder
        @AllArgsConstructor(access = AccessLevel.PROTECTED)
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        public static class SmsVerificationResultResponse {
            String phone;
            Boolean isCodeValid;
        }
    }
}
