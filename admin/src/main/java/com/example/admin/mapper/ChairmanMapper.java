package com.example.admin.mapper;

import com.example.admin.entity.Chairman;
import com.example.admin.model.chairmen.CreateChairmanRequest;
import com.example.admin.model.chairmen.TableChairmanResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ChairmanMapper {
    @Mapping(target = "avatar", source = "savedAvatar")
    @Mapping(target = "password", source = "encodedPassword")
    Chairman createChairman(CreateChairmanRequest createChairmanRequest, String savedAvatar,
                            String encodedPassword);
    List<TableChairmanResponse> chairmanListToTableChairmanResponseList(List<Chairman> chairmanList);
    @Mapping(target = "fullName", expression = "java(chairman.getLastName()+\" \"+chairman.getFirstName()+\" \"+chairman.getMiddleName())")
    TableChairmanResponse chairmanToTableChairmanResponse(Chairman chairman);
}
