package com.example.training.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
    public void uploadFile(MultipartFile multipartFile) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        amazonS3.putObject(bucket, multipartFile.getOriginalFilename(), multipartFile.getInputStream(), metadata);
        log.info("File uploaded: " + multipartFile.getOriginalFilename());
    }

    @Override
    public byte[] getFileBytes(String fileName) {
        try (S3ObjectInputStream objectContent = amazonS3.getObject(bucket, fileName).getObjectContent();) {
            return IOUtils.toByteArray(objectContent);
        } catch (IOException e) {
            throw new RuntimeException("Can't cast to ByteArray {}", e);
        }
    }
}
