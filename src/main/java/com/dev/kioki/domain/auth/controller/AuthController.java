package com.dev.kioki.domain.auth.controller;

import com.dev.kioki.domain.auth.dto.AuthDTO.AuthResponse.*;
import com.dev.kioki.domain.auth.dto.AuthDTO.AuthRequest.*;
import com.dev.kioki.domain.auth.service.AuthService;
import com.dev.kioki.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
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
    public BaseResponse<TokenResponse> join(@Valid @RequestBody JoinRequest request) {
        return BaseResponse.onSuccess(authService.join(request));
    }

    @GetMapping("/reissueToken")
    @Operation(summary="토큰 재발급 API", description="AccessToken의 유효 기간이 만료된 경우, Authorization 헤더에 RefreshToken을 담아서 요청을 보내주세요.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다."),
    })
    public BaseResponse<TokenResponse> reissueToken(@Parameter(name = "refreshToken", hidden = true) String refreshToken) {
        return BaseResponse.onSuccess(authService.reissueToken(refreshToken));
    }

    @GetMapping("/logout")
    @Operation(summary="로그아웃 API", description="로그아웃 API입니다. 로그아웃 시, 기존에 발급된 유효한 토큰을 무효화 처리합니다." )
    public BaseResponse<?> signOut(
            @Parameter(name = "accessToken", hidden = true) String accessToken) {
        return BaseResponse.onSuccess("로그아웃 성공");
    }

    @PostMapping("/login")
    @Operation(summary="로그인 API", description="로그인 성공 시, token을 생성해서 반환합니다." )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다.")
    })
    public BaseResponse<TokenResponse> localLogin(@RequestBody SmsVerificationRequest request) {
        return BaseResponse.onSuccess(null);
    }

    @PostMapping("/sms/send")
    @Operation(summary="휴대폰 인증 코드 전송 요청 API", description="휴대폰 인증 번호 전송을 요청하는 API입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다.")
    })
    public BaseResponse<SmsVerificationCodeResponse> sendCode(@RequestBody SmsSendRequest request) {
        return BaseResponse.onSuccess(authService.sendCode(request));
    }

    @PostMapping("/sms/verify")
    @Operation(summary="휴대폰 인증 코드 일치 여부 확인 요청 API", description="휴대폰 인증 코드 일치 여부를 확인해주는 API입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다.")
    })
    public BaseResponse<SmsVerificationResultResponse> verifyCode(@RequestBody SmsVerificationRequest request) {
        return BaseResponse.onSuccess(authService.verifyCode(request));
    }
}
