package com.example.admin.service;

import com.example.admin.model.general.SelectSearchRequest;
import com.example.admin.model.houses.CityResponse;
import com.example.admin.model.houses.StreetResponse;
import org.springframework.data.domain.Page;

public interface NovaPostService {
    Page<CityResponse> getCities(SelectSearchRequest selectSearchRequest);
    Page<StreetResponse> getStreets(SelectSearchRequest selectSearchRequest, String cityRef);
}
