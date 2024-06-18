package com.example.user.mapper;

import com.example.user.entity.Chairman;
import com.example.user.model.messages.ChairmanResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ChairmanMapper {
    List<ChairmanResponse> chairmanListToChairmanResponseList(List<Chairman> chairmanList);
    @Mapping(target = "id", source = "id")
    @Mapping(target = "fullName", expression = "java(chairman.getLastName()+\" \"+chairman.getFirstName()+\" \"+chairman.getMiddleName())")
    ChairmanResponse chairmanToChairmanResponse(Chairman chairman);
}
