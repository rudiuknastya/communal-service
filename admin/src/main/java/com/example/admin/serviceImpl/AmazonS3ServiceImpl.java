package com.example.admin.serviceImpl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.example.admin.service.AmazonS3Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public class AmazonS3ServiceImpl implements AmazonS3Service {
    @Value("${aws.s3.bucket}")
    private String bucketName;
    private final AmazonS3 s3client;
    private final Logger logger = LogManager.getLogger(AmazonS3ServiceImpl.class);
    public AmazonS3ServiceImpl(AmazonS3 s3client) {
        this.s3client = s3client;
    }

    @Override
    public void uploadMultipartFile(String keyName, MultipartFile file) {
        logger.info("uploadMultipartFile() - Uploading multipart file "+file.getOriginalFilename());
        try {
            s3client.putObject(bucketName, keyName, file.getInputStream(), null);
            logger.info("uploadMultipartFile() - Multipart file has been uploaded");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void uploadFile(String name, File file) {
        logger.info("uploadFile() - Uploading file "+file.getName());
        try {
            s3client.putObject(bucketName, name, new FileInputStream(file), null);
            logger.info("uploadFile() - File has been uploaded");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public S3Object getS3Object(String fileName) {
        logger.info("getFile() - Getting file "+fileName);
        S3Object s3Object = s3client.getObject(bucketName, fileName);
        logger.info("getFile() - File has been got");
        return s3Object;
    }

    @Override
    public void deleteFile(String fileName) {
        logger.info("getFile() - Deleting file "+fileName);
        s3client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
        logger.info("getFile() - File has been deleted");
    }
}
