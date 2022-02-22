package com.example.training.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AmazonS3ServiceImplTest {
    private static final String BUCKET = "amazon-s3-ap";
    private static final String PATH_TO_FILE = "/home/volodymyr/IdeaProjects/training/scrin.png";
    private static final String FILE = "file";
    private static final String FILE_NAME = "scrin.png";
    private static final String TEXT_PLAIN = "text/plain";
    private final AmazonS3 client = mock(AmazonS3.class);
    private final AmazonS3Service amazonS3Service = new AmazonS3ServiceImpl(client, BUCKET);

    @Test
    void uploadFileNullPointerException() {
        assertThrows(NullPointerException.class, () -> amazonS3Service.uploadFile(null));
    }

    @Test
    void uploadFileBucketIfAbsent() throws Exception {
        when(client.doesBucketExistV2(BUCKET)).thenReturn(false);
        amazonS3Service.uploadFile(getMultipartFile());
        verify(client, times(1)).createBucket(BUCKET);
    }

    @Test
    void uploadFileSuccess() throws Exception {
        MultipartFile multipartFile = getMultipartFile();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(Objects.requireNonNull(multipartFile).getSize());

        when(client.doesBucketExistV2(BUCKET)).thenReturn(true);
        when(client.getObjectMetadata(BUCKET, FILE_NAME)).thenReturn(objectMetadata);
        amazonS3Service.uploadFile(multipartFile);
        ArgumentCaptor<PutObjectRequest> putObjectRequestCaptor = ArgumentCaptor.forClass(PutObjectRequest.class);
        verify(client).putObject(putObjectRequestCaptor.capture());

        PutObjectRequest putObjectRequest = putObjectRequestCaptor.getValue();
        assertEquals(objectMetadata.getContentLength(), putObjectRequest.getMetadata().getContentLength());
        assertEquals(Objects.requireNonNull(multipartFile).getInputStream().available(), putObjectRequest.getInputStream().available());
        assertEquals(FILE_NAME, putObjectRequest.getKey());
        assertEquals(BUCKET, putObjectRequest.getBucketName());
        verify(client, times(1)).putObject(any(PutObjectRequest.class));
    }

    @Test
    void getFileBytesFileNotFoundException() {
        when(client.doesObjectExist(BUCKET, FILE_NAME)).thenReturn(false);
        assertThrows(FileNotFoundException.class, () -> amazonS3Service.getFileBytes(FILE_NAME));
    }

    @Test
    void getFileBytesSuccess() throws IOException {
        FileSystemResource sourceFile = new FileSystemResource(PATH_TO_FILE);
        S3Object s3Object = new S3Object();
        s3Object.setObjectContent(sourceFile.getInputStream());
        MultipartFile multipartFile = getMultipartFile();
        when(client.doesObjectExist(BUCKET, FILE_NAME)).thenReturn(true);
        when(client.getObject(BUCKET, FILE_NAME)).thenReturn(s3Object);
        assertArrayEquals(Objects.requireNonNull(multipartFile).getBytes(),
                amazonS3Service.getFileBytes(multipartFile.getOriginalFilename()));
    }

    @Test
    void deleteSuccess() {
        when(client.doesObjectExist(BUCKET, FILE_NAME)).thenReturn(true);
        assertThat(amazonS3Service.delete(FILE_NAME), is(true));
    }

    @Test
    void deleteObjectNotExist() {
        when(client.doesObjectExist(BUCKET, FILE_NAME)).thenReturn(false);
        assertThat(amazonS3Service.delete(null), is(false));
    }

    private MultipartFile getMultipartFile() {
        File file = new File(PATH_TO_FILE);
        try (FileInputStream input = new FileInputStream(file)) {
            return new MockMultipartFile(FILE,
                    file.getName(), TEXT_PLAIN, IOUtils.toByteArray(input));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}