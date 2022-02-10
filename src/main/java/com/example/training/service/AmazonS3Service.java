package com.example.training.service;

import org.springframework.web.multipart.MultipartFile;

public interface AmazonS3Service {
    void uploadFile(MultipartFile file);

    byte[] downloadFile(final String fileName);
}
