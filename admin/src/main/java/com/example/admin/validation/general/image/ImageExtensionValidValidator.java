package com.example.admin.validation.general.image;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class ImageExtensionValidValidator implements ConstraintValidator<ImageExtensionValid, MultipartFile> {
    private final Logger logger = LogManager.getLogger(ImageExtensionValidValidator.class);
    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            Tika tika = new Tika();
            String detectedType = null;
            try {
                detectedType = tika.detect(multipartFile.getBytes());
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
            if (detectedType != null) {
                return isExtensionValid(detectedType);
            }
        }
        return true;
    }
    private boolean isExtensionValid(String detectedType){
        return detectedType.equals("image/png")
                || detectedType.equals("image/jpg")
                || detectedType.equals("image/jpeg");
    }
}
