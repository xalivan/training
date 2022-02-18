package com.example.training.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

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
        if (multipartFile.isEmpty()) {
            throw new FileNotFoundException();
        }
        createBucketIfAbsent();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3.putObject(
                    new PutObjectRequest(bucket, multipartFile.getOriginalFilename(), inputStream, objectMetadata));
        }
    }

    @Override
    public byte[] getFileBytes(String fileName) throws IOException {
        if (amazonS3.doesObjectExist(bucket, fileName)) {
            try (S3ObjectInputStream objectContent = amazonS3.getObject(bucket, fileName).getObjectContent()) {
                return IOUtils.toByteArray(objectContent);
            }
        } else {
            throw new FileNotFoundException();
        }
    }

    @Override
    public boolean delete(String fileName) {
        if (amazonS3.doesObjectExist(bucket, fileName)) {
            DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, fileName);
            amazonS3.deleteObject(deleteObjectRequest);
            return true;
        } else {
            return false;
        }
    }

    private void createBucketIfAbsent() {
        if (!amazonS3.doesBucketExistV2(bucket)) {
            log.warn("Bucket is not available");
            amazonS3.createBucket(bucket);
            log.info("Created bucket {}", bucket);
        }
    }
}
