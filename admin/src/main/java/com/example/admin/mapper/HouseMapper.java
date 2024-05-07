package com.example.admin.mapper;

import com.example.admin.entity.Chairman;
import com.example.admin.entity.House;
import com.example.admin.model.houses.HouseRequest;
import com.example.admin.model.houses.TableHouseResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface HouseMapper {
    @Mapping(target = "chairman", source = "chairman")
    @Mapping(target = "status", source = "houseRequest.status")
    @Mapping(target = "id", ignore = true)
    House createHouse(HouseRequest houseRequest, Chairman chairman);
    List<TableHouseResponse> houseListToTableHouseResponseList(List<House> houses);
    @Mapping(target = "id", source = "id")
    @Mapping(target = "chairman", expression = "java(house.getChairman().getLastName()+\" \"+house.getChairman().getFirstName()+\" \"+house.getChairman().getMiddleName())")
    TableHouseResponse houseToTableHouseResponse(House house);
}
