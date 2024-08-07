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
    }
}
