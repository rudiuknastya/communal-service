package com.example.admin.mapper;

import com.example.admin.entity.Chairman;
import com.example.admin.entity.House;
import com.example.admin.model.houses.*;
import com.example.admin.model.user.FilterHouseResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

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
    @Mapping(target = "chairmanNameResponse.id", source = "chairman.id")
    @Mapping(target = "chairmanNameResponse.fullName", expression = "java(chairman.getLastName()+\" \"+chairman.getFirstName()+\" \"+chairman.getMiddleName())")
    HouseResponse houseToHouseResponse(House house);
    @Mapping(target = "chairman", source = "chairman")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", source = "houseRequest.status")
    void updateHouse(@MappingTarget House house, HouseRequest houseRequest, Chairman chairman);
    List<HouseNumberResponse> houseListToHouseNumberResponseList(List<House> houses);
    @Mapping(target = "id", source = "id")
    HouseNumberResponse houseToHouseNumberResponse(House house);
    default List<String> houseListToCityStringList(List<House> houses){
        return houses.stream().map(House::getCity).toList();
    }
    default List<String> houseListToStreetStringList(List<House> houses){
        return houses.stream().map(House::getStreet).toList();
    }
    FilterHouseResponse houseToFilterHouseResponse(House house);
}
