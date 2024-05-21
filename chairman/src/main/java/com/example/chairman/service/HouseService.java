package com.example.chairman.service;

import com.example.chairman.model.general.SelectSearchRequest;
import org.springframework.data.domain.Page;

public interface HouseService {
    Page<String> getCities(SelectSearchRequest selectSearchRequest);
    Page<String> getStreets(SelectSearchRequest selectSearchRequest, String city, String number);
    Page<String> getNumbers(SelectSearchRequest selectSearchRequest, String city, String street);
}
