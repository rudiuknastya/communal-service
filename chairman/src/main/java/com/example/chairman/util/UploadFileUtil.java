package com.example.chairman.util;

import com.example.chairman.service.AmazonS3Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.UUID;

@Component
public class UploadFileUtil {
    private final AmazonS3Service amazonS3Service;
    private final Logger logger = LogManager.getLogger(UploadFileUtil.class);

    public UploadFileUtil(AmazonS3Service amazonS3Service) {
        this.amazonS3Service = amazonS3Service;
    }

    public String saveMultipartFile(MultipartFile multipartFile) {
        logger.info("saveMultipartFile - Saving multipart file");
        String uuidFile = UUID.randomUUID().toString();
        String fileName = uuidFile + "." + multipartFile.getOriginalFilename();
        amazonS3Service.uploadMultipartFile(fileName, multipartFile);
        logger.info("saveMultipartFile - Multipart file has been saved");
        return fileName;
    }
    public void deleteFile(String fileName){
        logger.info("deleteFile - Deleting file");
        amazonS3Service.deleteFile(fileName);
        logger.info("deleteFile - File has been deleted");
    }
}
