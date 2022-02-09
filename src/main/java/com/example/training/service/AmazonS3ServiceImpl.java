package com.example.training.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class AmazonS3ServiceImpl implements AmazonS3Service {
    private final AmazonS3 amazonS3;
    private final String bucket;
    private final String address = "/home/volodymyr/IdeaProjects/training/src/main/java/com/example/training/download/";

    public AmazonS3ServiceImpl(AmazonS3 amazonS3,
                               @Value("${aws.s3.bucket}") String bucket) {
        this.amazonS3 = amazonS3;
        this.bucket = bucket;
    }

    @Override
    public boolean save(String fileName, MultipartFile file) {
        System.out.format("Uploading \"%s\" to S3 bucket \"%s\"\n", fileName, bucket);
        try {
            amazonS3.putObject(bucket, fileName, file.getName());
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
        System.out.println("Done!");
        return false;
    }

    @Override
    public boolean getS3Object(String fileName) {
        S3Object object = amazonS3.getObject(bucket, fileName);
        try (S3ObjectInputStream inputStream = object.getObjectContent();
             FileOutputStream outputStream = new FileOutputStream(getName(fileName))) {
            byte[] read_buf = new byte[1024];
            int read_len = 0;
            while ((read_len = inputStream.read(read_buf)) > 0) {
                outputStream.write(read_buf, 0, read_len);
            }
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        System.out.format("Downloaded \"%s\" from S3 bucket \"%s\"\n", fileName, bucket);
        return false;
    }

    private String getName(String fileName) {
        return address + fileName;
    }
}
