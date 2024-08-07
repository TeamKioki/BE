package com.dev.kioki.domain.auth.service.impl;

import com.dev.kioki.domain.auth.dto.AuthDTO;
import com.dev.kioki.domain.auth.service.AuthService;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Override
    public AuthDTO.TokenResponse join(AuthDTO.JoinRequest request) {
        return null;
    }

    @Override
    public AuthDTO.TokenResponse reissueToken(String reissueToken) {
        return null;
    }
}
