package com.example.user.service;

import com.example.user.model.general.SelectSearchRequest;
import com.example.user.model.house.HouseNumberResponse;
import org.springframework.data.domain.Page;

public interface HouseService {
    Page<String> getCitiesForSelect(SelectSearchRequest selectSearchRequest);
    Page<String> getStreetsForSelect(SelectSearchRequest selectSearchRequest, String city, String number);
    Page<HouseNumberResponse> getNumbersForSelect(SelectSearchRequest selectSearchRequest, String city, String street);
}
