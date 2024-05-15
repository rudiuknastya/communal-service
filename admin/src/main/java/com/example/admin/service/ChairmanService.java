package com.example.admin.service;

import com.example.admin.model.chairmen.*;
import com.example.admin.model.general.SelectSearchRequest;
import com.example.admin.model.houses.ChairmanNameResponse;
import org.springframework.data.domain.Page;

public interface ChairmanService {
    void createChairman(CreateChairmanRequest createChairmanRequest);
    Page<TableChairmanResponse> getChairmenForTable(FilterRequest filterRequest);
    void deleteChairman(Long id);
    boolean checkIfPossibleToDelete(Long id);
    ChairmanResponse getChairmanResponse(Long id);
    void updateChairman(EditChairmanRequest editChairmanRequest, Long id);
    Page<ChairmanNameResponse> getChairmanNameResponses(SelectSearchRequest selectSearchRequest);
}
