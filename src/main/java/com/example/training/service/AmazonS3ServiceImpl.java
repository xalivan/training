package com.example.training.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    public void uploadFile(MultipartFile file) {
        log.info("Uploading \"{}\" to S3 bucket \"{}\"\n", file.getOriginalFilename(), bucket);
        amazonS3.putObject(bucket, file.getOriginalFilename(), file.getOriginalFilename());
    }

    @Override
    @Async
    public byte[] downloadFile(final String fileName) {
        byte[] content = null;
        final S3Object s3Object = amazonS3.getObject(bucket, fileName);
        try (S3ObjectInputStream stream = s3Object.getObjectContent()) {
            content = IOUtils.toByteArray(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}
