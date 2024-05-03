package com.example.admin.service;

import com.example.admin.model.chairmen.CreateChairmanRequest;
import com.example.admin.model.chairmen.FilterRequest;
import com.example.admin.model.chairmen.TableChairmanResponse;
import org.springframework.data.domain.Page;

public interface ChairmanService {
    void createChairman(CreateChairmanRequest createChairmanRequest);
    Page<TableChairmanResponse> getChairmenForTable(FilterRequest filterRequest);
    boolean deleteChairman(Long id);
}
