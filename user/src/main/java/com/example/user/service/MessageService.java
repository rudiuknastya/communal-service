package com.example.user.service;

import com.example.user.model.messages.MessageRequest;
import com.example.user.model.messages.ViewMessageResponse;

public interface MessageService {
    void createMessage(MessageRequest messageRequest);
    ViewMessageResponse getViewMessageResponse(Long id);
    void deleteMessage(Long id);
}
