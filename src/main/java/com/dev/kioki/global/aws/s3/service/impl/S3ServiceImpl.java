package com.dev.kioki.global.aws.s3.service.impl;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.dev.kioki.global.aws.s3.dto.PresignedUrlToDownloadDTO;
import com.dev.kioki.global.aws.s3.dto.PresignedUrlToUploadDTO;
import com.dev.kioki.global.aws.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

    private final AmazonS3 amazonS3;

    @Value("${S3_BUCKET}")
    private String bucket;

    @Override
    public PresignedUrlToUploadDTO getPresignedUrlToUpload(String fileName) {

        Date expiration = new Date();
        long expTime = expiration.getTime();
        expTime += TimeUnit.MINUTES.toMillis(3);
        expiration.setTime(expTime);

        String keyName = UUID.randomUUID() + "_" + fileName;

        GeneratePresignedUrlRequest generatePresignedRequest = new GeneratePresignedUrlRequest(bucket, keyName)
                .withMethod(HttpMethod.PUT)
                .withExpiration(expiration);

        String key = generatePresignedRequest.getKey();

        return new PresignedUrlToUploadDTO(amazonS3.generatePresignedUrl(generatePresignedRequest).toString(), key);
    }

    @Override
    public PresignedUrlToDownloadDTO getPresignedUrlToDownload(String keyName) {

        Date expiration = new Date();
        long expTime = expiration.getTime();
        expTime += TimeUnit.MINUTES.toMillis(3);
        expiration.setTime(expTime);

        GeneratePresignedUrlRequest generatePresignedRequest = new GeneratePresignedUrlRequest(bucket, keyName)
                .withMethod(HttpMethod.GET)
                .withExpiration(expiration);

        return new PresignedUrlToDownloadDTO(amazonS3.generatePresignedUrl(generatePresignedRequest).toString());
    }
}
