package com.dev.kioki.global.aws.s3.service;

import com.dev.kioki.global.aws.s3.dto.PresignedUrlToDownloadDTO;
import com.dev.kioki.global.aws.s3.dto.PresignedUrlToUploadDTO;

public interface S3Service {
    PresignedUrlToUploadDTO getPresignedUrlToUpload(String fileName);

    PresignedUrlToDownloadDTO getPresignedUrlToDownload(String keyName);

    String generateStaticUrl(String keyName);
}
