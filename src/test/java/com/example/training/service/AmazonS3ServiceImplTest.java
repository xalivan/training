package com.example.training.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

class AmazonS3ServiceImplTest {
    private static final String BUCKET = "amazon-s3-ap";
    private static final String FILE_NAME = "hello.txt";
    private static final MultipartFile MULTIPART_FILE =
            new MockMultipartFile("file", FILE_NAME, MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());
    private static final MultipartFile MULTIPART_FILE_NULL =
            new MockMultipartFile("file", null, null, new byte[0]);
    private final AmazonS3 client = mock(AmazonS3.class);
    private final AmazonS3Service amazonS3Service = new AmazonS3ServiceImpl(client, BUCKET);

    @Test
    void uploadFileNotFoundException() {
        Throwable thrown = catchThrowable(() -> amazonS3Service.uploadFile(MULTIPART_FILE_NULL));
        assertThat(thrown.getClass(), is(FileNotFoundException.class));
    }

    @Test
    void uploadFileIfBucketAbsences() throws Exception {
        when(client.doesBucketExistV2(BUCKET)).thenReturn(false);
        amazonS3Service.uploadFile(MULTIPART_FILE);
        verify(client).createBucket(BUCKET);
    }

    @Test
    void uploadFileSuccess() throws Exception {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(MULTIPART_FILE.getSize());
        when(client.doesBucketExistV2(BUCKET)).thenReturn(true);
        when(client.getObjectMetadata(BUCKET, MULTIPART_FILE.getOriginalFilename())).thenReturn(objectMetadata);
        amazonS3Service.uploadFile(MULTIPART_FILE);
        ArgumentCaptor<PutObjectRequest> putObjectRequestCaptor = ArgumentCaptor.forClass(PutObjectRequest.class);
        verify(client).putObject(putObjectRequestCaptor.capture());
        PutObjectRequest putObjectRequest = putObjectRequestCaptor.getValue();
        assertThat(putObjectRequest.getBucketName(), is(BUCKET));
        assertThat(putObjectRequest.getKey(), is(MULTIPART_FILE.getOriginalFilename()));
        assertThat(putObjectRequest.getInputStream().readAllBytes().length,
                is(MULTIPART_FILE.getInputStream().readAllBytes().length));
        assertThat(putObjectRequest.getMetadata().getContentLength(), is(objectMetadata.getContentLength()));
    }

    @Test
    void getFileBytesFileNotFoundException() {
        when(client.doesObjectExist(BUCKET, FILE_NAME)).thenReturn(false);
        Throwable thrown = catchThrowable(() -> amazonS3Service.getFileBytes(FILE_NAME));
        assertThat(thrown.getClass(), is(FileNotFoundException.class));
    }

    @Test
    void getFileBytesSuccess() throws IOException {
        S3Object s3Object = new S3Object();
        s3Object.setObjectContent(MULTIPART_FILE.getInputStream());
        when(client.doesObjectExist(BUCKET, MULTIPART_FILE.getOriginalFilename())).thenReturn(true);
        when(client.getObject(BUCKET, MULTIPART_FILE.getOriginalFilename())).thenReturn(s3Object);
        assertThat(amazonS3Service.getFileBytes(MULTIPART_FILE.getOriginalFilename()), is(MULTIPART_FILE.getBytes()));
    }

    @Test
    void deleteSuccess() {
        when(client.doesObjectExist(BUCKET, FILE_NAME)).thenReturn(true);
        assertThat(amazonS3Service.delete(FILE_NAME), is(true));
    }

    @Test
    void deleteObjectIfNotExist() {
        when(client.doesObjectExist(BUCKET, FILE_NAME)).thenReturn(false);
        assertThat(amazonS3Service.delete(null), is(false));
    }
}