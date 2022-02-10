package com.example.training.controller;

import com.example.training.service.AmazonS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("amazon")
@RequiredArgsConstructor
public class AmazonController {
    private final AmazonS3Service amazonS3Service;

    @GetMapping("download/{fileName}")
    public ResponseEntity<ByteArrayResource> getObjectS3ByName(@PathVariable String fileName) {
        final byte[] data = amazonS3Service.downloadFile(fileName);
        final ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity.ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

    @PostMapping(
            path = "upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> save(@RequestParam("file") MultipartFile file) {
        try {
            amazonS3Service.uploadFile(file);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
