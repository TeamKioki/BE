package com.dev.kioki.global.security.filter;

import com.dev.kioki.global.error.handler.AuthException;
import com.dev.kioki.global.redis.RedisUtil;
import com.dev.kioki.global.security.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import java.util.Date;

import static com.dev.kioki.global.common.code.status.ErrorStatus.AUTH_EXPIRED_TOKEN;

@RequiredArgsConstructor
@Slf4j
public class CustomLogoutHandler implements LogoutHandler {
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        try {
            String accessToken = jwtUtil.resolveToken(request);

            Long now = new Date().getTime();
            Long expiration = jwtUtil.getExpiration(accessToken) - now;
            redisUtil.setValue(accessToken, "logout", expiration);

            redisUtil.deleteValue(jwtUtil.getPhone(accessToken));
        } catch (ExpiredJwtException e) {
            throw new AuthException(AUTH_EXPIRED_TOKEN);
        }
    }
}
