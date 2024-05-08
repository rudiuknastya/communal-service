package com.example.admin.service;

import com.example.admin.model.general.SelectSearchRequest;
import com.example.admin.model.houses.*;
import org.springframework.data.domain.Page;

public interface HouseService {
    void createHouse(HouseRequest houseRequest);
    Page<TableHouseResponse> getHouseResponsesForTable(FilterRequest filterRequest);
    HouseResponse getHouseResponse(Long id);
    void updateHouse(HouseRequest houseRequest, Long id);
    Page<String> getCities(SelectSearchRequest selectSearchRequest);
    Page<String> getStreets(SelectSearchRequest selectSearchRequest, String city, Long number);
    Page<HouseNumberResponse> getNumbers(SelectSearchRequest selectSearchRequest, String city, String street);
}
