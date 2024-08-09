package com.dev.kioki.global.security.config;

import com.dev.kioki.global.config.WebConfig;
import com.dev.kioki.global.security.filter.JwtExceptionFilter;
import com.dev.kioki.global.security.filter.JwtFilter;
import com.dev.kioki.global.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity(debug = true)
public class SecurityConfig {

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

        http.addFilterAfter(new JwtFilter(jwtUtil, allowUrls), CorsFilter.class);
        http.addFilterBefore(new JwtExceptionFilter(allowUrls), JwtFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
