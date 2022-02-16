package com.example.training.controller;

import com.amazonaws.AmazonClientException;
import com.example.training.service.AmazonS3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
@RequestMapping("amazon/s3/files")
@RequiredArgsConstructor
public class AmazonS3Controller {
    private final AmazonS3Service amazonS3Service;

    @GetMapping(path = "{fileName}", produces = MediaType.ALL_VALUE)
    public ResponseEntity<byte[]> getFileContent(@PathVariable String fileName) {
        try {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .body(amazonS3Service.getFileBytes(fileName));
        } catch (IOException | AmazonClientException e) {
            e.printStackTrace();
            log.info("File {} not found", fileName);
            return ResponseEntity.badRequest().body(("File " + fileName + " not found").getBytes(StandardCharsets.UTF_8));
        }
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            log.info("File not added");
            return ResponseEntity.badRequest().body(("File not added"));
        }
        try {
            return ResponseEntity.ok().body(amazonS3Service.uploadFile(file));
        } catch (IOException | AmazonClientException e) {
            e.printStackTrace();
            log.info("File {} not saved", file.getOriginalFilename());
            return ResponseEntity.badRequest().body("File " + file.getOriginalFilename() + " not saved");
        }
    }
}
