package com.example.admin.service;

import com.amazonaws.services.s3.model.S3Object;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface AmazonS3Service {
    void uploadMultipartFile(String keyName, MultipartFile file);
    void uploadFile(String name, File file);
    S3Object getS3Object(String keyName);
    void deleteFile(String fileName);
}
