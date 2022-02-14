package com.example.training.controller;

import com.example.training.service.AmazonS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("amazon")
@RequiredArgsConstructor
public class AmazonController {
    private final AmazonS3Service amazonS3Service;

    @GetMapping(path = "download/files/{fileName}", produces = MediaType.ALL_VALUE)
    public ResponseEntity<byte[]> getObjectS3ByName(@PathVariable String fileName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.add("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        return ResponseEntity.ok()
                .headers(headers)
                .body(amazonS3Service.getFileBytes(fileName));
    }

    @PostMapping(path ="upload/files", produces = MediaType.ALL_VALUE)
    public ResponseEntity<Void> save(@RequestParam("file") MultipartFile file) {
        try {
            amazonS3Service.uploadFile(file);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
