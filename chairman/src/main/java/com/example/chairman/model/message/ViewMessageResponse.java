package com.example.chairman.model.message;

import java.time.LocalDateTime;

public record ViewMessageResponse(
        String subject,
        String text,
        String creationDate
) {
}
