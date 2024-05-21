package com.example.chairman.mapper;

import com.example.chairman.entity.House;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface HouseMapper {
    default List<String> houseListToCityStringList(List<House> houses){
        return houses.stream().map(House::getCity).toList();
    }
    default List<String> houseListToStreetStringList(List<House> houses){
        return houses.stream().map(House::getStreet).toList();
    }
    default List<String> houseListToNumberStringList(List<House> houses){
        return houses.stream().map(House::getNumber).toList();
    }

}
