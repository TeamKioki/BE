package com.dev.kioki.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class AuthDTO {

    @Getter
    public static class JoinRequest {
        @NotBlank String name;
        @NotBlank String phone;
        String imageName;
        @NotNull int age;
        String introduction;
        @NotBlank String kioskDifficulty;
    }

    @Getter
    public static class TokenResponse {
        String accessToken;
        String refreshToken;
    }
}
