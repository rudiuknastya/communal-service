package com.example.admin.model.user;

import com.example.admin.validation.general.image.FileNotEmpty;
import com.example.admin.validation.user.file.XlsxFileExtension;
import org.springframework.web.multipart.MultipartFile;

public record XlsxFileRequest(
        @FileNotEmpty(message = "Файл не був завантажений")
//        @XlsxFileExtension
        MultipartFile xlsxFile
) {
}
