package com.example.chairman.controller;

import com.example.chairman.model.chat.PageRequest;
import com.example.chairman.model.chat.UserResponse;
import com.example.chairman.service.ChatService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("")
    public ModelAndView getChatPage(){
        return new ModelAndView("chat/chat");
    }
    @GetMapping("/get-users")
    public @ResponseBody Page<UserResponse> getChatUsers(PageRequest pageRequest){
        return chatService.getUsersForChat(pageRequest);
    }

}
