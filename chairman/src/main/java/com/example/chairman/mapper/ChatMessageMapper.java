package com.example.chairman.mapper;

import com.example.chairman.entity.Chat;
import com.example.chairman.entity.ChatMessage;
import com.example.chairman.entity.enums.ContentType;
import com.example.chairman.entity.enums.Sender;
import com.example.chairman.model.chat.ChatNotification;
import com.example.chairman.model.chatMessage.ChatMessageResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ChatMessageMapper {
    @Mapping(target = "chat", source = "chat")
    @Mapping(target = "content", source = "content")
    @Mapping(target = "creationDate", source = "creationDate")
    @Mapping(target = "creationTime", source = "creationTime")
    @Mapping(target = "contentType", source = "contentType")
    @Mapping(target = "sender", source = "sender")
    @Mapping(target = "id", ignore = true)
    ChatMessage createChatMessage(Chat chat, String content, LocalDate creationDate,
                                  LocalTime creationTime, ContentType contentType,
                                  Sender sender);
    List<ChatMessageResponse> chatMessageListToChatMessageResponseList(List<ChatMessage> chatMessages);
    @Mapping(target = "id", source = "id")
    ChatMessageResponse chatMessageToChatMessageResponse(ChatMessage chatMessage);
    @Mapping(target = "recipientId", source = "recipientId")
    @Mapping(target = "messageId", source = "chatMessage.id")
    @Mapping(target = "senderId", source = "senderId")
    ChatNotification createChatNotification(ChatMessage chatMessage, Long recipientId,
                                            Long senderId);
}
