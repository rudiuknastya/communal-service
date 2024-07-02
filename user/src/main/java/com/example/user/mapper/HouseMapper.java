package com.example.user.mapper;

import com.example.user.entity.House;
import com.example.user.model.house.HouseNumberResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface HouseMapper {
    default List<String> houseListToCityStringList(List<House> houses){
        return houses.stream().map(House::getCity).toList();
    }
    default List<String> houseListToStreetStringList(List<House> houses){
        return houses.stream().map(House::getStreet).toList();
    }
    List<HouseNumberResponse> houseListToHouseNumberResponseList(List<House> houses);
    @Mapping(target = "id", source = "id")
    HouseNumberResponse houseToHouseNumberResponse(House house);
}
