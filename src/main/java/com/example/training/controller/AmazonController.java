package com.example.training.controller;

import com.example.training.service.AmazonS3Service;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<Void> getObjectS3ByName(@PathVariable String fileName) {
        return amazonS3Service.getS3Object(fileName) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @PostMapping(
            path = "upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> save(@RequestParam("fileName") String fileName,
                                     @RequestParam("file") MultipartFile file) {
        return amazonS3Service.save(fileName, file) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }
}
