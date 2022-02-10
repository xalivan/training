package com.example.training.controller;

import com.example.training.service.AmazonS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.Produces;


@RestController
@RequestMapping("amazon")
@RequiredArgsConstructor
public class AmazonController {
    private final AmazonS3Service amazonS3Service;

    @GetMapping("download/files/{fileName}")
    @Produces(MediaType.ALL_VALUE)
    public ResponseEntity<byte[]> getObjectS3ByName(@PathVariable String fileName) {
        byte[] bytes = amazonS3Service.getFileBytes(fileName);
        return ResponseEntity.ok()
                .headers(getHttpHeaders(fileName))
                .body(bytes);
    }

    @PostMapping("upload/files")
    @Produces(MediaType.ALL_VALUE)
    public ResponseEntity<Void> save(@RequestParam("file") MultipartFile file) {
        try {
            amazonS3Service.uploadFile(file);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    private HttpHeaders getHttpHeaders(String fileName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.add("Content-type", "application/octet-stream");
        headers.add("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        return headers;
    }
}
