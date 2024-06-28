package com.example.chairman.controller;

import com.example.chairman.model.chat.ChatNotification;
import com.example.chairman.model.chat.PageRequest;
import com.example.chairman.model.chat.UserResponse;
import com.example.chairman.model.chatMessage.ChatMessageResponse;
import com.example.chairman.model.chatMessage.ChatTextMessageRequest;
import com.example.chairman.service.ChatMessageService;
import com.example.chairman.service.ChatService;
import org.springframework.data.domain.Page;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ChatController {
    private final ChatService chatService;
    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(ChatService chatService, ChatMessageService chatMessageService,
                          SimpMessagingTemplate messagingTemplate) {
        this.chatService = chatService;
        this.chatMessageService = chatMessageService;
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping("/chat")
    public ModelAndView getChatPage(){
        return new ModelAndView("chat/chat");
    }
    @GetMapping("/chat/get-users")
    public @ResponseBody Page<UserResponse> getChatUsers(PageRequest pageRequest){
        return chatService.getUsersForChat(pageRequest);
    }
    @GetMapping("/chat/messages/{userId}/{chairmanId}")
    public @ResponseBody Page<ChatMessageResponse> getChatMessages(PageRequest pageRequest,
                                                                   @PathVariable("userId") Long userId,
                                                                   @PathVariable("chairmanId") Long chairmanId){
        System.out.println(pageRequest.toString());
        return chatMessageService.getChatMessages(pageRequest, userId, chairmanId);
    }
    @MessageMapping("/chat")
    public void proceedTextMessage(@Payload ChatTextMessageRequest chatTextMessageRequest){
        ChatNotification chatNotification = chatMessageService.createChatTextMessage(chatTextMessageRequest);
        messagingTemplate.convertAndSendToUser(
                chatNotification.recipientId().toString(),
                "/"+chatNotification.sender()+"/queue/messages",
                chatNotification
        );
    }

}
