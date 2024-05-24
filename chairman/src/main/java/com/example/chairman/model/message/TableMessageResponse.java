package com.example.chairman.model.message;

import java.time.LocalDateTime;

public record TableMessageResponse(
        Long id,
        String fullName,
        Long apartmentNumber,
        String phoneNumber,
        String subject,
        String creationDate
) {
}
