package com.dev.kioki.domain.auth.service.impl;

import com.dev.kioki.domain.auth.dto.AuthDTO.AuthResponse.*;
import com.dev.kioki.domain.auth.dto.AuthDTO.AuthRequest.*;
import com.dev.kioki.domain.auth.service.AuthService;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Override
    public TokenResponse join(JoinRequest request) {
        return null;
    }

    @Override
    public TokenResponse reissueToken(String reissueToken) {
        return null;
    }
}
