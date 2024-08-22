package com.dev.kioki.global.aws.s3.controller;

import com.dev.kioki.global.aws.s3.dto.PresignedUrlToDownloadDTO;
import com.dev.kioki.global.aws.s3.dto.PresignedUrlToUploadDTO;
import com.dev.kioki.global.aws.s3.service.S3Service;
import com.dev.kioki.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/s3")
@Tag(name = "S3 파일 업로드 관련 컨트롤러")
public class S3Controller {

    private final S3Service s3Service;

    @Operation(summary = "파일 업로드용 presignedUrl 생성 요청", description = "파일 업로드를 위한 presignedUrl 생성을 요청하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공입니다."),
    })
    @GetMapping("/presigned/upload")
    public BaseResponse<PresignedUrlToUploadDTO> getPresignedUrlToUpload(
            @RequestParam(value = "fileName") String fileName
    ) {
        return BaseResponse.onSuccess(s3Service.getPresignedUrlToUpload(fileName));
    }
}
