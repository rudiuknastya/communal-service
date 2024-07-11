package com.example.user.service;

import com.amazonaws.services.s3.model.S3Object;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface AmazonS3Service {
    void uploadFile(String name, File file);
    S3Object getS3Object(String keyName);
}
