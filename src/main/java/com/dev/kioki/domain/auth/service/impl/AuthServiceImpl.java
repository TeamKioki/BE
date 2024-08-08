package com.dev.kioki.domain.auth.service.impl;

import com.dev.kioki.domain.auth.converter.AuthConverter;
import com.dev.kioki.domain.auth.dto.AuthDTO.AuthResponse.*;
import com.dev.kioki.domain.auth.dto.AuthDTO.AuthRequest.*;
import com.dev.kioki.domain.auth.service.AuthService;
import com.dev.kioki.global.error.handler.SmsException;
import com.dev.kioki.global.redis.RedisUtil;
import com.dev.kioki.global.sms.SmsUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.dev.kioki.global.common.code.status.ErrorStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final SmsUtil smsUtil;
    private final RedisUtil redisUtil;
    @Value("${sms.auth-code-expiration-seconds}")
    private Long authCodeExpirationSeconds;

    @Override
    public TokenResponse join(JoinRequest request) {
        return null;
    }

    @Override
    public TokenResponse reissueToken(String refreshToken) {
        return null;
    }

    @Override
    public SmsVerificationCodeResponse sendCode(SmsSendRequest request) {
        try {
            String code = smsUtil.createCode();
            smsUtil.sendCode(request.getPhone(), code);

            redisUtil.setValue(request.getPhone(), code, authCodeExpirationSeconds);

            return AuthConverter.toSmsVerificationCodeResponse(request.getPhone(), code);
        } catch (SmsException e) {
            throw new SmsException(SMS_CODE_SEND_FAIL);
        }
    }

    @Override
    public SmsVerificationResultResponse verifyCode(SmsVerificationRequest request) {
        String redisCode = redisUtil.getValue(request.getPhone());

        if (redisCode == null) {
            throw new SmsException(SMS_CODE_NOT_FOUND);
        }

        boolean isCodeValid = request.getCode().equals(redisCode);
        if (isCodeValid) {
            redisUtil.deleteValue(request.getPhone());
        } else {
            throw new SmsException(INCORRECT_SMS_CODE);
        }

        return AuthConverter.toSmsVerificationResultResponse(request.getPhone(), isCodeValid);
    }
}
