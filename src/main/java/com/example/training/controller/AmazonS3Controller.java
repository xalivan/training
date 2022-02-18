package com.example.training.controller;

import com.example.training.service.AmazonS3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("amazon/s3/files")
@RequiredArgsConstructor
public class AmazonS3Controller {
    private final AmazonS3Service amazonS3Service;

    @GetMapping(path = "{fileName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> getFileContent(@PathVariable String fileName) {
        try {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", fileName))
                    .body(amazonS3Service.getFileBytes(fileName));
        } catch (FileNotFoundException e) {
            log.error(String.format("File %s not found. %s", fileName, e.getMessage()));
            return ResponseEntity.badRequest().build();
        } catch (IOException e) {
            log.error("This file {} cannot be processed. {}", fileName, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody MultipartFile file) {
        try {
            amazonS3Service.uploadFile(file);
            return ResponseEntity.ok().build();
        } catch (FileNotFoundException e) {
            log.error("Multipart file is empty. {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (IOException e) {
            log.error("File {} not saved. {}", file.getOriginalFilename(), e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping(path = "{fileName}")
    public ResponseEntity<Void> delete(@PathVariable String fileName) {
        return amazonS3Service.delete(fileName)
                ? ResponseEntity.ok().build()
                : ResponseEntity.badRequest().build();
    }
}
