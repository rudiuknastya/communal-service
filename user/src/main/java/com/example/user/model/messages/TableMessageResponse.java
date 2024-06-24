package com.example.user.model.messages;

public record TableMessageResponse(
        Long id,
        String subject,
        String creationDate
) {
}
