package com.dev.kioki.global.security.util;

import com.dev.kioki.domain.user.entity.User;
import com.dev.kioki.domain.user.repository.UserRepository;
import com.dev.kioki.global.error.handler.AuthException;
import com.dev.kioki.global.error.handler.SmsException;
import com.dev.kioki.global.redis.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import static com.dev.kioki.global.common.code.status.ErrorStatus.*;

@RequiredArgsConstructor
@Slf4j
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final RedisUtil redisUtil;
    private final UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) {

        User user = userRepository.findByPhone(authentication.getPrincipal().toString())
                .orElseThrow(() -> new AuthException(USER_PHONE_NOT_FOUND));

        String redisCode = redisUtil.getValue(authentication.getPrincipal().toString());

        if (redisCode == null) {
            throw new AuthException(SMS_CODE_NOT_FOUND);
        }

        boolean isCodeValid = authentication.getCredentials().equals(redisCode);
        if (isCodeValid) {
            redisUtil.deleteValue(authentication.getPrincipal().toString());
        } else {
            throw new SmsException(INCORRECT_SMS_CODE);
        }

        return new CustomAuthenticationToken(
                authentication.getPrincipal().toString(),
                authentication.getCredentials().toString(),
                user.getId().toString());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
