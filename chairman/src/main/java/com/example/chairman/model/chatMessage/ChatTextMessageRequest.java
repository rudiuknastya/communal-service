package com.example.chairman.model.chatMessage;

import com.example.chairman.entity.enums.Sender;

public record ChatTextMessageRequest(
        String text,
        Long userId,
        Long chairmanId,
        Sender sender
) {
}
