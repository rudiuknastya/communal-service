package com.example.admin.mapper;

import com.example.admin.entity.Chairman;
import com.example.admin.entity.House;
import com.example.admin.model.houses.HouseRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface HouseMapper {
    @Mapping(target = "chairman", source = "chairman")
    @Mapping(target = "status", source = "houseRequest.status")
    @Mapping(target = "id", ignore = true)
    House createHouse(HouseRequest houseRequest, Chairman chairman);
}
