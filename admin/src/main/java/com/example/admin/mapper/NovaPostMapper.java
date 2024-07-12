package com.example.admin.mapper;

import com.example.admin.model.houses.CityResponse;
import com.example.admin.model.houses.StreetResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface NovaPostMapper {
    @Mapping(target = "city", source = "description")
    @Mapping(target = "ref", source = "ref")
    CityResponse createCityResponse(String description, String ref);
    @Mapping(target = "street", source = "description")
    StreetResponse createStreetResponse(String description);
}
