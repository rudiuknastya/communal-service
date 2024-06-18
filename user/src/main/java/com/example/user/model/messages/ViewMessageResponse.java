package com.example.user.model.messages;

import java.time.LocalDateTime;

public record ViewMessageResponse (
        String subject,
        String text,
        String creationDate
){
}
