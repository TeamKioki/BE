package com.dev.kioki.global.aws.s3.service;

import com.dev.kioki.global.aws.s3.dto.PresignedUrlToUploadDTO;

public interface S3Service {
    PresignedUrlToUploadDTO getPresignedUrlToUpload(String fileName);
}
