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

    @GetMapping(path = "files/{fileName}", produces = MediaType.ALL_VALUE)
    public ResponseEntity<byte[]> getObjectS3ByName(@PathVariable String fileName) {
        try {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .body(amazonS3Service.getFileBytes(fileName));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(path ="files", produces = MediaType.ALL_VALUE)
    public ResponseEntity<Void> save(@RequestParam("file") MultipartFile file) {
        try {
            amazonS3Service.uploadFile(file);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
