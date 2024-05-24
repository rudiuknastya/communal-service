package com.example.chairman.service;

import com.example.chairman.model.message.FilterRequest;
import com.example.chairman.model.message.TableMessageResponse;
import org.springframework.data.domain.Page;

public interface MessageService {
    Page<TableMessageResponse> getMessageResponsesForTable(FilterRequest filterRequest);

}
