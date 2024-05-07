package com.example.admin.mapper;

import com.example.admin.entity.Chairman;
import com.example.admin.entity.House;
import com.example.admin.model.houses.HouseRequest;
import com.example.admin.model.houses.HouseResponse;
import com.example.admin.model.houses.TableHouseResponse;
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
}
