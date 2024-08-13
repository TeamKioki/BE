package com.dev.kioki.domain.auth.service.impl;

import com.dev.kioki.domain.auth.converter.AuthConverter;
import com.dev.kioki.domain.auth.dto.AuthDTO.AuthResponse.*;
import com.dev.kioki.domain.auth.dto.AuthDTO.AuthRequest.*;
import com.dev.kioki.domain.auth.service.AuthService;
import com.dev.kioki.domain.group.service.GroupService;
import com.dev.kioki.domain.user.entity.User;
import com.dev.kioki.domain.user.repository.UserRepository;
import com.dev.kioki.global.error.handler.AuthException;
import com.dev.kioki.global.error.handler.SmsException;
import com.dev.kioki.global.redis.RedisUtil;
import com.dev.kioki.global.security.util.JwtUtil;
import com.dev.kioki.global.sms.SmsUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.dev.kioki.global.common.code.status.ErrorStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

    private final SmsUtil smsUtil;
    private final RedisUtil redisUtil;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    @Value("${sms.auth-code-expiration-seconds}")
    private Long authCodeExpirationSeconds;

    private final GroupService groupService;

    @Override
    @Transactional
    public TokenResponse join(JoinRequest request) {
        User user = AuthConverter.toUser(request);
        userRepository.save(user);

        createGroupForUser(user);

        String accessToken = jwtUtil.createAccessToken(user.getId().toString(), user.getPhone(), user.getUserRole().toString());
        String refreshToken = jwtUtil.createRefreshToken(user.getId().toString(), user.getPhone(), user.getUserRole().toString());

        redisUtil.setValue(user.getId().toString(), refreshToken, jwtUtil.getRefreshTokenValiditySec());

        return AuthConverter.toTokenResponse(accessToken, refreshToken);
    }

    private void createGroupForUser(User user) {
        // 예외 처리를 고려한 그룹 생성 로직
        try {
            groupService.createGroup(user.getId());
        } catch (Exception e) {
            // 그룹 생성 실패 시의 로직 처리
            throw new RuntimeException("Failed to create group for user: " + user.getId(), e);
        }
    }

    @Override
    @Transactional
    public TokenResponse reissueToken(String refreshToken) {
        try {
            if (!jwtUtil.isTokenValid(refreshToken)) {
                throw new AuthException(AUTH_INVALID_TOKEN);
            }

            String userId = jwtUtil.getUserId(refreshToken);
            String phone = jwtUtil.getPhone(refreshToken);
            String role = jwtUtil.getRole(refreshToken);

            redisUtil.deleteValue(userId);

            String newAccess = jwtUtil.createAccessToken(userId, phone, role);
            String newRefresh = jwtUtil.createRefreshToken(userId, phone, role);

            redisUtil.setValue(userId, newRefresh, jwtUtil.getRefreshTokenValiditySec());

            return AuthConverter.toTokenResponse(newAccess, newRefresh);
        } catch (IllegalArgumentException e) {
            throw new AuthException(AUTH_INVALID_TOKEN);
        } catch (ExpiredJwtException e) {
            throw new AuthException(AUTH_EXPIRED_TOKEN);
        }
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
