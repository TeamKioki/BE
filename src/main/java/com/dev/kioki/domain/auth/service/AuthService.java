package com.dev.kioki.domain.auth.service;

import com.dev.kioki.domain.auth.dto.AuthDTO.AuthResponse.*;
import com.dev.kioki.domain.auth.dto.AuthDTO.AuthRequest.*;

public interface AuthService {

    TokenResponse join(JoinRequest request);

    TokenResponse reissueToken(String refreshToken);
}
