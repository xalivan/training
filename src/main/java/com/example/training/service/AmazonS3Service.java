package com.example.training.service;

import org.springframework.web.multipart.MultipartFile;

public interface AmazonS3Service {
    boolean save(String fileName, MultipartFile file);

    boolean getS3Object(String fileName) ;
}
