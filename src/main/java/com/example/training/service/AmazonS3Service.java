package com.example.training.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AmazonS3Service {
    void uploadFile(MultipartFile file) throws IOException;

    byte[] getFileBytes(final String fileName);
}
