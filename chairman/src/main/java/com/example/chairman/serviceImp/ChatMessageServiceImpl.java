package com.example.chairman.serviceImp;

import com.example.chairman.entity.Chat;
import com.example.chairman.entity.ChatMessage;
import com.example.chairman.entity.enums.ContentType;
import com.example.chairman.entity.enums.Sender;
import com.example.chairman.mapper.ChatMessageMapper;
import com.example.chairman.model.chairman.ChairmanDetails;
import com.example.chairman.model.chat.ChatNotification;
import com.example.chairman.model.chatMessage.ChatMessageResponse;
import com.example.chairman.model.chatMessage.ChatTextMessageRequest;
import com.example.chairman.repository.ChatMessageRepository;
import com.example.chairman.service.ChatMessageService;
import com.example.chairman.service.ChatService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.example.chairman.specification.ChatMessageSpecification.byChat;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatMessageMapper chatMessageMapper;
    private final ChatService chatService;
    private final Logger logger = LogManager.getLogger(ChatMessageServiceImpl.class);

    public ChatMessageServiceImpl(ChatMessageRepository chatMessageRepository, ChatMessageMapper chatMessageMapper,
                                  ChatService chatService) {
        this.chatMessageRepository = chatMessageRepository;
        this.chatMessageMapper = chatMessageMapper;
        this.chatService = chatService;
    }

    @Override
    public ChatNotification createChatTextMessage(ChatTextMessageRequest chatTextMessageRequest) {
        logger.info("createChatTextMessage - Creating text chat message "+chatTextMessageRequest.toString());
        Chat chat = chatService.getChat(chatTextMessageRequest.chairmanId(), chatTextMessageRequest.userId());
        ChatMessage chatMessage = chatMessageMapper
                .createChatMessage(chat, chatTextMessageRequest.text(),
                        LocalDate.now(), LocalTime.now(), ContentType.TEXT,
                        chatTextMessageRequest.sender());
        ChatMessage savedChatMessage = chatMessageRepository.save(chatMessage);
        ChatNotification chatNotification = createChatNotification(savedChatMessage, chatTextMessageRequest.chairmanId(), chatTextMessageRequest.userId());
        logger.info("createChatTextMessage - Text chat message has been created");
        return chatNotification;
    }
    private ChatNotification createChatNotification(ChatMessage chatMessage, Long chairmanId, Long userId){
        ChatNotification chatNotification;
        if(chatMessage.getSender().equals(Sender.CHAIRMAN)){
            chatNotification = chatMessageMapper.createChatNotification(chatMessage, userId, chairmanId);
        } else {
            chatNotification = chatMessageMapper.createChatNotification(chatMessage, chairmanId, userId);
        }
        return chatNotification;
    }
    @Override
    public Page<ChatMessageResponse> getChatMessages(com.example.chairman.model.chat.PageRequest pageRequest, Long userId, Long chairmanId) {
        logger.info("getChatMessages - Getting chat messages by user id "+userId+ " "+pageRequest.toString());
        Pageable pageable = PageRequest.of(pageRequest.page(), 20, Sort.Direction.DESC, "id");
        Page<ChatMessage> chatMessagePage = getFilteredMessages(pageable, userId, chairmanId);
        List<ChatMessageResponse> chatMessageResponses = chatMessageMapper
                .chatMessageListToChatMessageResponseList(chatMessagePage.getContent());
        Page<ChatMessageResponse> chatMessageResponsePage = new PageImpl<>(chatMessageResponses, pageable, chatMessagePage.getTotalElements());
        logger.info("getChatMessages - Chat messages have been got");
        return chatMessageResponsePage;
    }

    private Page<ChatMessage> getFilteredMessages(Pageable pageable, Long userId, Long chairmanId) {
        Chat chat = chatService.getChat(chairmanId, userId);
        return chatMessageRepository.findAll(byChat(chat), pageable);
    }
}
