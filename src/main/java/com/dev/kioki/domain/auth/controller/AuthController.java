package com.dev.kioki.domain.auth.controller;

import com.dev.kioki.domain.auth.dto.AuthDTO;
import com.dev.kioki.domain.auth.service.AuthService;
import com.dev.kioki.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "유저 인증 관련 컨트롤러")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/join")
    @Operation(summary="회원 가입 API", description="회원가입 API 입니다." )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON201", description = "요청 성공 및 리소스 생성됨"),
    })
    public BaseResponse<AuthDTO.TokenResponse> join(@Valid @RequestBody AuthDTO.JoinRequest request) {
        return BaseResponse.onSuccess(authService.join(request));
    }
}
