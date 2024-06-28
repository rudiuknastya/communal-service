package com.example.chairman.service;

import com.example.chairman.entity.ChatMessage;
import com.example.chairman.model.chat.ChatNotification;
import com.example.chairman.model.chat.PageRequest;
import com.example.chairman.model.chatMessage.ChatMessageResponse;
import com.example.chairman.model.chatMessage.ChatTextMessageRequest;
import org.springframework.data.domain.Page;

public interface ChatMessageService {
    ChatNotification createChatTextMessage(ChatTextMessageRequest chatTextMessageRequest);
    Page<ChatMessageResponse> getChatMessages(PageRequest pageRequest, Long userId, Long chairmanId);
}
