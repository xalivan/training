package com.example.training.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
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
    public void uploadFile(MultipartFile multipartFile) throws IOException {
        if (!amazonS3.doesBucketExistV2(bucket)) {
            log.info("Bucket is not available");
            amazonS3.createBucket(bucket);
            log.info(bucket + " created");
        }
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        InputStream inputStream = multipartFile.getInputStream();
        amazonS3.putObject(new PutObjectRequest(bucket, multipartFile.getOriginalFilename(), inputStream, objectMetadata));
        inputStream.close();
    }

    @Override
    public byte[] getFileBytes(String fileName) throws IOException {
        S3ObjectInputStream objectContent = amazonS3.getObject(bucket, fileName).getObjectContent();
        byte[] bytes = IOUtils.toByteArray(objectContent);
        objectContent.close();
        return bytes;
    }
}
