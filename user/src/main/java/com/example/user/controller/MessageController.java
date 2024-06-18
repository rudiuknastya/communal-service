package com.example.user.controller;

import com.example.user.model.general.SelectSearchRequest;
import com.example.user.model.messages.ChairmanResponse;
import com.example.user.model.messages.MessageRequest;
import com.example.user.service.ChairmanService;
import com.example.user.service.MessageService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("messages")
public class MessageController {
    private final MessageService messageService;
    private final ChairmanService chairmanService;

    public MessageController(MessageService messageService, ChairmanService chairmanService) {
        this.messageService = messageService;
        this.chairmanService = chairmanService;
    }

    @GetMapping("/new")
    public ModelAndView getCreateMessagePage(){
        return new ModelAndView("messages/create-message");
    }
    @PostMapping("/new")
    public @ResponseBody ResponseEntity<?> createMessage(@Valid @ModelAttribute MessageRequest messageRequest){
        messageService.createMessage(messageRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/get-chairmen")
    public @ResponseBody Page<ChairmanResponse> getChairmen(SelectSearchRequest selectSearchRequest){
        return chairmanService.getChairmanResponsesForSelect(selectSearchRequest);
    }
}
