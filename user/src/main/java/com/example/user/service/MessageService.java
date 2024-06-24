package com.example.user.service;

import com.example.user.model.messages.FilterRequest;
import com.example.user.model.messages.MessageRequest;
import com.example.user.model.messages.TableMessageResponse;
import com.example.user.model.messages.ViewMessageResponse;
import org.springframework.data.domain.Page;

public interface MessageService {
    void createMessage(MessageRequest messageRequest);
    ViewMessageResponse getViewMessageResponse(Long id);
    void deleteMessage(Long id);
    Page<TableMessageResponse> getMessageResponsesForTable(FilterRequest filterRequest);
    void deleteMessages(Long []ids);
}
