package com.dev.kioki.global.security.filter;

import com.dev.kioki.domain.auth.converter.AuthConverter;
import com.dev.kioki.domain.auth.dto.AuthDTO.AuthRequest.*;
import com.dev.kioki.global.error.handler.AuthException;
import com.dev.kioki.global.redis.RedisUtil;
import com.dev.kioki.global.security.util.CustomAuthenticationToken;
import com.dev.kioki.global.security.util.HttpRequestUtil;
import com.dev.kioki.global.security.util.HttpResponseUtil;
import com.dev.kioki.global.security.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

import static com.dev.kioki.global.common.code.status.SuccessStatus._OK;

public class CustomLoginFilter extends AbstractAuthenticationProcessingFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;

    public CustomLoginFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil, RedisUtil redisUtil) {
        super(new AntPathRequestMatcher("/api/v1/auth/login"));
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.redisUtil = redisUtil;

    }

    @Override
    public Authentication attemptAuthentication(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response) throws IOException, ServletException, AuthException {

        SmsVerificationRequest smsVerificationRequest = HttpRequestUtil.readBody(request, SmsVerificationRequest.class);

        return authenticationManager.authenticate(new CustomAuthenticationToken(smsVerificationRequest.getPhone(), smsVerificationRequest.getCode(), null));
    }

    @Override
    protected void successfulAuthentication(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain chain,
            @NonNull Authentication authResult) throws IOException, ServletException {

        String accessToken = jwtUtil.createAccessToken(authResult.getName(), authResult.getPrincipal().toString());
        String refreshToken = jwtUtil.createRefreshToken(authResult.getName(), authResult.getPrincipal().toString());

        redisUtil.setValue(authResult.getName(), refreshToken, jwtUtil.getRefreshTokenValiditySec());

        HttpResponseUtil.setSuccessResponse(response, _OK, AuthConverter.toTokenResponse(accessToken, refreshToken));
    }
}
