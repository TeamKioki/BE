package com.dev.kioki.domain.auth.converter;

import com.dev.kioki.domain.auth.dto.AuthDTO.AuthResponse.*;

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
}
