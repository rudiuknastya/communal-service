package com.example.chairman.service;

import com.amazonaws.services.s3.model.S3Object;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface AmazonS3Service {
    void uploadMultipartFile(String keyName, MultipartFile file);
    S3Object getS3Object(String keyName);
    void deleteFile(String fileName);
}
