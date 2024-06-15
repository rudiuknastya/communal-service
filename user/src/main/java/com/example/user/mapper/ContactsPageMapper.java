package com.example.user.mapper;

import com.example.user.entity.ContactsPage;
import com.example.user.model.contactsPage.ContactsPageResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ContactsPageMapper {
    ContactsPageResponse contactsPageToContactsPageResponse(ContactsPage contactsPage);
}
