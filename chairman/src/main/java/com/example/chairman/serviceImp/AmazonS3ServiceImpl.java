package com.example.chairman.serviceImp;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.example.chairman.service.AmazonS3Service;
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
    private final AmazonS3 amazonS3;
    private final Logger logger = LogManager.getLogger(AmazonS3ServiceImpl.class);
    public AmazonS3ServiceImpl(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    @Override
    public void uploadMultipartFile(String keyName, MultipartFile file) {
        logger.info("uploadMultipartFile() - Uploading multipart file "+file.getOriginalFilename());
        try {
            amazonS3.putObject(bucketName, keyName, file.getInputStream(), null);
            logger.info("uploadMultipartFile() - Multipart file has been uploaded");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public S3Object getS3Object(String fileName) {
        logger.info("getFile() - Getting file "+fileName);
        S3Object s3Object = amazonS3.getObject(bucketName, fileName);
        logger.info("getFile() - File has been got");
        return s3Object;
    }

    @Override
    public void deleteFile(String fileName) {
        logger.info("getFile() - Deleting file "+fileName);
        amazonS3.deleteObject(new DeleteObjectRequest(bucketName, fileName));
        logger.info("getFile() - File has been deleted");
    }
}
