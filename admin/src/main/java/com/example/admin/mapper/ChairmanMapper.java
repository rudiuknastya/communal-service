package com.example.admin.mapper;

import com.example.admin.entity.Chairman;
import com.example.admin.model.chairmen.CreateChairmanRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ChairmanMapper {
    @Mapping(target = "avatar", source = "savedAvatar")
    @Mapping(target = "password", source = "encodedPassword")
    Chairman createChairman(CreateChairmanRequest createChairmanRequest, String savedAvatar,
                            String encodedPassword);
}
