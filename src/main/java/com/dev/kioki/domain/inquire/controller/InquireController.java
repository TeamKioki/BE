package com.dev.kioki.domain.inquire.controller;


import com.dev.kioki.domain.inquire.dto.InquireRequestDTO;
import com.dev.kioki.domain.inquire.entity.Inquire;
import com.dev.kioki.domain.inquire.service.InquireCommandService;
import com.dev.kioki.domain.inquire.converter.InquireConverter;
import com.dev.kioki.domain.user.converter.UserConverter;
import com.dev.kioki.domain.user.dto.UserResponseDTO;
import com.dev.kioki.domain.user.service.UserQueryService;
import com.dev.kioki.global.common.BaseResponse;
import com.dev.kioki.domain.inquire.dto.InquireResponseDTO;
import com.dev.kioki.global.validation.annotation.ExistPage;
import com.dev.kioki.global.validation.annotation.ExistUser;
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

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/inquires")
@Tag(name = "문의 관련 컨트롤러")
public class InquireController {

    private final InquireCommandService inquireCommandService;
    private final UserQueryService userQueryService;

    @PostMapping("/add")
    @Operation(summary = "문의 작성 API", description = "문의를 작성하는 API입니다.")
    public BaseResponse<InquireResponseDTO.CreateInqireResultDTO> createInquire(@RequestBody @Valid InquireRequestDTO.InquireDTO request, @ExistUser @RequestParam(name = "user_id") Long user_id) {
        Inquire inquire = inquireCommandService.createInquire(user_id, request);
        return BaseResponse.onSuccess(InquireConverter.toCreateInquireResultDTO(inquire));
    }

}