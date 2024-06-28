package com.example.chairman.model.chatMessage;

import com.example.chairman.entity.enums.ContentType;
import com.example.chairman.entity.enums.Sender;

public record ChatMessageResponse(
        Long id,
        String content,
        String creationDate,
        String creationTime,
        ContentType contentType,
        Sender sender
) {
}
