package com.example.user.mapper;

import com.example.user.entity.Chairman;
import com.example.user.entity.Message;
import com.example.user.entity.User;
import com.example.user.model.messages.MessageRequest;
import com.example.user.model.messages.TableMessageResponse;
import com.example.user.model.messages.ViewMessageResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface MessageMapper {
    @Mapping(target = "creationDate", source = "creationDate")
    @Mapping(target = "chairman", source = "chairman")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    Message messageRequestToMessage(MessageRequest messageRequest, LocalDateTime creationDate,
                                    User user, Chairman chairman);
    ViewMessageResponse messageToViewMessageResponse(Message message);
    List<TableMessageResponse> messageListToTableMessageResponseList(List<Message> messages);
    @Mapping(target = "id", source = "id")
    TableMessageResponse messageToTableMessageResponse(Message message);
}
