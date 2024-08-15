package com.dev.kioki.domain.user.controller;

import com.dev.kioki.domain.inquire.entity.Inquire;
import com.dev.kioki.domain.kiosk.entity.Model;
import com.dev.kioki.domain.review.entity.Review;
import com.dev.kioki.domain.user.converter.UserConverter;
import com.dev.kioki.domain.user.dto.UserRequestDTO;
import com.dev.kioki.domain.user.dto.UserResponseDTO;
import com.dev.kioki.domain.user.entity.User;
import com.dev.kioki.domain.user.service.UserCommandService;
import com.dev.kioki.domain.user.service.UserQueryService;
import com.dev.kioki.global.common.BaseResponse;
import com.dev.kioki.global.validation.annotation.ExistPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/users")
public class UserController {

    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    @Tag(name = "마이페이지 관련 컨트롤러")
    @GetMapping("/{user_id}")
    @Operation(summary = "회원 정보 조회 API", description = "회원의 정보를 조회하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "access 토큰 만료",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "access 토큰 모양이 이상함",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "user_id", description = "유저의 아이디입니다!"),
    })
    public BaseResponse<UserResponseDTO.UserInfoDTO> getUserInfo(@PathVariable(name = "user_id") Long user_id) {
        Optional<User> user = userQueryService.getUserInfo(user_id);
        return BaseResponse.onSuccess(UserConverter.userInfoDTO(user));
    }

    @Tag(name = "마이페이지 관련 컨트롤러")
    @GetMapping("/{user_id}/reviews")
    @Operation(summary = "리뷰 목록 조회 API", description = "나의 리뷰 목록을 조회하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "access 토큰 만료",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "access 토큰 모양이 이상함",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "user_id", description = "유저의 아이디입니다!"),
            @Parameter(name = "page", description = "페이지 번호, 1번이 1 페이지 입니다."),
    })
    public BaseResponse<UserResponseDTO.ReviewPreViewListDTO> getReviewList(@PathVariable(name = "user_id") Long user_id, @ExistPage @RequestParam(name = "page") Integer page){
        Page<Review> reviews = userQueryService.getReviewList(user_id, page);
        return BaseResponse.onSuccess(UserConverter.reviewPreViewListDTO(reviews));
    }

    @Tag(name = "마이페이지 관련 컨트롤러")
    @GetMapping("/{user_id}/inquires")
    @Operation(summary = "문의 목록 조회 API", description = "나의 문의 목록을 조회하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "access 토큰 만료",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "access 토큰 모양이 이상함",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "user_id", description = "유저의 아이디입니다!"),
            @Parameter(name = "page", description = "페이지 번호, 1번이 1 페이지 입니다."),
    })
    public BaseResponse<UserResponseDTO.InquirePreViewListDTO> getInquireList(@PathVariable(name = "user_id") Long user_id, @ExistPage @RequestParam(name = "page") Integer page){
        Page<Inquire> inquires = userQueryService.getInquireList(user_id, page);
        return BaseResponse.onSuccess(UserConverter.inquirePreViewListDTO(inquires));
    }

    @Tag(name ="내 키오스크 관리 관련 컨트롤러")
    @PostMapping("/{user_id}/kiosk")
    @Operation(summary = "키오스크 모델 추가", description = "유저에게 키오스크를 추가합니다")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "access 토큰 만료",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "access 토큰 모양이 이상함",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "user_id", description = "유저 ID"),
    })
    public BaseResponse<UserResponseDTO.UserModelDTO> addModel(@PathVariable(name = "user_id") Long user_id, @RequestBody(required = true) UserRequestDTO.UserModelDto userModel){
        Long modelId = userModel.getModelId();

        Model updatedModel = userCommandService.addModelToUser(user_id, modelId);
        return BaseResponse.onSuccess(UserConverter.userModelDTO(updatedModel));
    }

}