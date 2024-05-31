package com.example.chairman.model.invoice;

import com.example.chairman.validation.invoice.file.FileExtensionValid;
import com.example.chairman.validation.invoice.file.FileNotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public record InvoiceRequest(
        @NotNull(message = "Поле не може бути порожнім")
        Long userId,
        @FileNotEmpty
        @FileExtensionValid
        MultipartFile file
) {
}
