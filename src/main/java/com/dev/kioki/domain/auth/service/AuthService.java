package com.dev.kioki.domain.auth.service;


import com.dev.kioki.domain.auth.dto.AuthDTO;

public interface AuthService {

    AuthDTO.TokenResponse join(AuthDTO.JoinRequest request);

    AuthDTO.TokenResponse reissueToken(String refreshToken);
}
