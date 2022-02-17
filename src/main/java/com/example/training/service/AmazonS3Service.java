package com.example.training.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AmazonS3Service {
    String uploadFile(MultipartFile file) throws IOException;

    byte[] getFileBytes(String fileName) throws IOException;

    boolean delete(String fileName);
}
