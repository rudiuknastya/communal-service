package com.example.admin.mapper;

import com.example.admin.entity.Chairman;
import com.example.admin.model.chairmen.ChairmanResponse;
import com.example.admin.model.chairmen.CreateChairmanRequest;
import com.example.admin.model.chairmen.EditChairmanRequest;
import com.example.admin.model.chairmen.TableChairmanResponse;
import com.example.admin.model.houses.ChairmanNameResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

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
    ChairmanResponse chairmanToChairmanResponse(Chairman chairman);
    @Mapping(target = "password", source = "encodedPassword")
    @Mapping(target = "avatar", source = "savedAvatar")
    void updateChairmanWithPassword(@MappingTarget Chairman chairman, String savedAvatar,
                                    EditChairmanRequest editChairmanRequest, String encodedPassword);
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "avatar", source = "savedAvatar")
    void updateChairmanWithoutPassword(@MappingTarget Chairman chairman, String savedAvatar,
                                       EditChairmanRequest editChairmanRequest);
    List<ChairmanNameResponse> chairmanListToChairmanNameResponseList(List<Chairman> chairmanList);
    @Mapping(target = "fullName", expression = "java(chairman.getLastName()+\" \"+chairman.getFirstName()+\" \"+chairman.getMiddleName())")
    ChairmanNameResponse chairmanToChairmanNameResponse(Chairman chairman);
}
