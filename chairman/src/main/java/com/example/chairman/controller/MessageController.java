package com.example.chairman.controller;

import com.example.chairman.model.message.FilterRequest;
import com.example.chairman.model.message.TableMessageResponse;
import com.example.chairman.model.message.ViewMessageResponse;
import com.example.chairman.service.MessageService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/messages")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("")
    public ModelAndView getMessagesPage(){
        return new ModelAndView("messages/messages");
    }
    @GetMapping("/{id}")
    public ModelAndView getViewMessagePage(){
        return new ModelAndView("messages/view-message");
    }
    @GetMapping("/get")
    public @ResponseBody Page<TableMessageResponse> getMessages(FilterRequest filterRequest){
        return messageService.getMessageResponsesForTable(filterRequest);
    }
    @GetMapping("/get/{id}")
    public @ResponseBody ViewMessageResponse getMessageForView(@PathVariable Long id){
        return messageService.getViewMessageResponse(id);
    }

}
