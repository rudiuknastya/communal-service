package com.example.chairman.service;

import com.example.chairman.model.contactsPage.ContactsPageRequest;
import com.example.chairman.model.contactsPage.ContactsPageResponse;

public interface ContactsPageService {
    void createContactsPageIfNotExist();
    ContactsPageResponse getContactsPageResponse();
    void updateContactsPage(ContactsPageRequest contactsPageRequest);
}
