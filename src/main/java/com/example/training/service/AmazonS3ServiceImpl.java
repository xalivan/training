package com.example.training.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
@Service
public class AmazonS3ServiceImpl implements AmazonS3Service {
    private final AmazonS3 amazonS3;
    private final String bucket;

    public AmazonS3ServiceImpl(AmazonS3 amazonS3,
                               @Value("${aws.s3.bucket}") String bucket) {
        this.amazonS3 = amazonS3;
        this.bucket = bucket;
    }

    @Override
    public void uploadFile(MultipartFile multipartFile) throws IOException {
        File upload = File.createTempFile(multipartFile.getName(), ".tmp");
        multipartFile.transferTo(upload);
        amazonS3.putObject(bucket, multipartFile.getOriginalFilename(), upload);
    }

    @Override
    @Async
    public byte[] getFileBytes(final String fileName) {
        S3ObjectInputStream objectContent = amazonS3.getObject(bucket, fileName).getObjectContent();
        try {
            return IOUtils.toByteArray(objectContent);
        } catch (IOException e) {
            throw new RuntimeException("Can't cast to ByteArray {}", e);
        }
    }
}
