package com.example.user.service;

import com.example.user.model.messages.MessageRequest;

public interface MessageService {
    void createMessage(MessageRequest messageRequest);
}
