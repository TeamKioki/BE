package com.dev.kioki.domain.auth.service;

import com.dev.kioki.domain.auth.dto.AuthDTO.AuthResponse.*;
import com.dev.kioki.domain.auth.dto.AuthDTO.AuthRequest.*;
import com.dev.kioki.domain.user.entity.User;

public interface AuthService {

    TokenResponse join(JoinRequest request);

    TokenResponse reissueToken(String refreshToken);

    SmsVerificationCodeResponse sendCode(SmsSendRequest request);

    SmsVerificationResultResponse verifyCode(SmsVerificationRequest request);

    QuitResponse quit(User user);
}
