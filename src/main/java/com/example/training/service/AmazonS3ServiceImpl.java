package com.example.training.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    public String uploadFile(MultipartFile multipartFile) throws IOException {
        if (!amazonS3.doesBucketExistV2(bucket)) {
            log.warn("Bucket is not available");
            amazonS3.createBucket(bucket);
            log.info("Created bucket {}", bucket);
        }

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3.putObject(
                    new PutObjectRequest(bucket, multipartFile.getOriginalFilename(), inputStream, objectMetadata));
            return "File \"" + multipartFile.getOriginalFilename() + "\" saved";
        }
    }

    @Override
    public byte[] getFileBytes(String fileName) throws IOException {
        byte[] bytes;
        try (S3ObjectInputStream objectContent = amazonS3.getObject(bucket, fileName).getObjectContent()) {
            bytes = IOUtils.toByteArray(objectContent);
        }
        return bytes;
    }

    @Override
    public boolean delete(String fileName) {
        ListObjectsV2Result result = amazonS3.listObjectsV2(bucket);
        if (result.getObjectSummaries().stream().anyMatch(file -> file.getKey().equals(fileName))) {
            new DeleteObjectsRequest(bucket).withKeys(fileName);
            return true;
        } else {
            return false;
        }
    }
}
