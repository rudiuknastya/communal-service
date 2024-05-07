package com.example.admin.service;

import com.example.admin.model.houses.FilterRequest;
import com.example.admin.model.houses.HouseRequest;
import com.example.admin.model.houses.HouseResponse;
import com.example.admin.model.houses.TableHouseResponse;
import org.springframework.data.domain.Page;

public interface HouseService {
    void createHouse(HouseRequest houseRequest);
    Page<TableHouseResponse> getHouseResponsesForTable(FilterRequest filterRequest);
    HouseResponse getHouseResponse(Long id);
    void updateHouse(HouseRequest houseRequest, Long id);
}
