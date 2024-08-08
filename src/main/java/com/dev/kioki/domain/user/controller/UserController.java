package com.dev.kioki.domain.user.controller;

import com.dev.kioki.domain.review.entity.Review;
import com.dev.kioki.domain.user.converter.UserConverter;
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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/users")
public class UserController {

    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    @GetMapping("/{user_id}/reviews")
    @Operation(summary = "마이페이지 리뷰 목록 조회 API", description = "마이페이지 리뷰 목록을 조회하는 API이며, 페이징을 포함합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "access 토큰 만료",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "access 토큰 모양이 이상함",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "user_id", description = "유저의 아이디, path variable 입니다!"),
            @Parameter(name = "page", description = "페이지 번호, 1번이 1 페이지 입니다."),
    })
    public BaseResponse<UserResponseDTO.ReviewPreViewListDTO> getReviewList(@PathVariable(name = "user_id") Long user_id, @ExistPage @RequestParam(name = "page") Integer page){
        Page<Review> reviews = userQueryService.getReviewList(user_id, page);
        return BaseResponse.onSuccess(UserConverter.reviewPreViewListDTO(reviews));
    }
}