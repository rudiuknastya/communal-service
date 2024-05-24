package com.example.chairman.mapper;

import com.example.chairman.entity.Message;
import com.example.chairman.model.message.TableMessageResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface MessageMapper {
    List<TableMessageResponse> messageListToTableMessageResponseList(List<Message> messages);
    @Mapping(target = "fullName", expression = "java(message.getUser().getLastName()+\" \"+message.getUser().getFirstName()+\" \"+message.getUser().getMiddleName())")
    @Mapping(target = "apartmentNumber", source = "user.apartmentNumber")
    @Mapping(target = "phoneNumber", source = "user.phoneNumber")
    @Mapping(target = "id", source = "message.id")
    TableMessageResponse messageToTableMessageResponse(Message message);
}
