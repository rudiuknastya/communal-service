package com.example.chairman.model.chat;

import com.example.chairman.entity.enums.ContentType;
import com.example.chairman.entity.enums.Sender;

public record ChatNotification(
        Long messageId,
        Long recipientId,
        Long senderId,
        String content,
        Sender sender,
        String creationDate,
        String creationTime,
        ContentType contentType
) {
}
