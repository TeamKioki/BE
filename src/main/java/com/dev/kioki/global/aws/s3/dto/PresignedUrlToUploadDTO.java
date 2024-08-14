package com.dev.kioki.global.aws.s3.dto;

public record PresignedUrlToUploadDTO(
        String url,
        String keyName
) {
}
