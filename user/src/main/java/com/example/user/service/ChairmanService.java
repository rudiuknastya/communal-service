package com.example.user.service;

import com.example.user.model.general.SelectSearchRequest;
import com.example.user.model.messages.ChairmanResponse;
import org.springframework.data.domain.Page;

public interface ChairmanService {
    Page<ChairmanResponse> getChairmanResponsesForSelect(SelectSearchRequest selectSearchRequest);
}
