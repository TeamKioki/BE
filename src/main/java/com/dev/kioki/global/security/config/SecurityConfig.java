package com.dev.kioki.global.security.config;

import com.dev.kioki.domain.user.repository.UserRepository;
import com.dev.kioki.global.common.code.status.SuccessStatus;
import com.dev.kioki.global.config.WebConfig;
import com.dev.kioki.global.redis.RedisUtil;
import com.dev.kioki.global.security.filter.CustomLoginFilter;
import com.dev.kioki.global.security.filter.CustomLogoutHandler;
import com.dev.kioki.global.security.filter.JwtExceptionFilter;
import com.dev.kioki.global.security.filter.JwtFilter;
import com.dev.kioki.global.security.util.CustomAuthenticationProvider;
import com.dev.kioki.global.security.util.HttpResponseUtil;
import com.dev.kioki.global.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity(debug = true)
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final RedisUtil redisUtil;
    private final UserRepository userRepository;

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        ProviderManager authenticationManager = null;

        try {
            authenticationManager = (ProviderManager) authenticationConfiguration.getAuthenticationManager();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        authenticationManager.getProviders().add(customAuthenticationProvider());

        return authenticationConfiguration.getAuthenticationManager();
    }

    public CustomAuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider(redisUtil, userRepository);
    }

    private static final String[] allowUrls = {
            "/swagger-ui/**",
            "/v3/**",
            "/api-docs/**",
            "/api/v1/auth/join/**",
            "/api/v1/auth/sms/**",
            "/api/v1/auth/reissueToken/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtUtil jwtUtil) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(cors -> cors
                .configurationSource(WebConfig.apiConfigurationSource()));

        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);

        http.headers(headers -> headers
                .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        http.sessionManagement(sessionManagement -> sessionManagement
                .sessionCreationPolicy((SessionCreationPolicy.STATELESS)));

        http.authorizeHttpRequests(requests -> requests
                .requestMatchers(allowUrls).permitAll()
                .requestMatchers("/**").authenticated()
                .anyRequest().permitAll());

        CustomLoginFilter customLoginFilter = new CustomLoginFilter(
                authenticationManager(), jwtUtil, redisUtil);

        http.logout(logout -> logout
                .logoutUrl("/api/v1/auth/logout")
                .addLogoutHandler(new CustomLogoutHandler(jwtUtil, redisUtil))
                .logoutSuccessHandler((request, response, authentication) -> HttpResponseUtil.setSuccessResponse(
                        response,
                        SuccessStatus._OK,
                        "로그아웃 성공"))
        );

        http.addFilterAt(customLoginFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new JwtFilter(jwtUtil, allowUrls, redisUtil), CustomLoginFilter.class);
        http.addFilterBefore(new JwtExceptionFilter(allowUrls), JwtFilter.class);
        http.addFilterAfter(new LogoutFilter((request, response, authentication)
                        -> HttpResponseUtil.setSuccessResponse(response, SuccessStatus._OK, "로그아웃 성공"),
                        new CustomLogoutHandler(jwtUtil, redisUtil)), JwtFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
