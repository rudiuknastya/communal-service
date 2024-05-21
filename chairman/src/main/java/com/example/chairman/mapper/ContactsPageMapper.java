package com.example.chairman.mapper;

import com.example.chairman.entity.ContactsPage;
import com.example.chairman.model.contactsPage.ContactsPageRequest;
import com.example.chairman.model.contactsPage.ContactsPageResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ContactsPageMapper {
    @Mapping(target = "firstTitle", source = "value")
    @Mapping(target = "firstText", source = "value")
    @Mapping(target = "secondTitle", source = "value")
    @Mapping(target = "secondText", source = "value")
    ContactsPage createFirstContactsPage(String value);
    ContactsPageResponse contactsPageToContactsPageResponse(ContactsPage contactsPage);
    void updateContactsPage(@MappingTarget ContactsPage contactsPage, ContactsPageRequest contactsPageRequest);
}
