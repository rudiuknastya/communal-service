package com.example.user.serviceImpl;

import com.example.user.entity.ContactsPage;
import com.example.user.mapper.ContactsPageMapper;
import com.example.user.model.contactsPage.ContactsPageResponse;
import com.example.user.repository.ContactsPageRepository;
import com.example.user.service.ContactsPageService;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class ContactsPageServiceImpl implements ContactsPageService {
    private final ContactsPageRepository contactsPageRepository;
    private final ContactsPageMapper contactsPageMapper;
    private final Logger logger = LogManager.getLogger(ContactsPageServiceImpl.class);
    public ContactsPageServiceImpl(ContactsPageRepository contactsPageRepository, ContactsPageMapper contactsPageMapper) {
        this.contactsPageRepository = contactsPageRepository;
        this.contactsPageMapper = contactsPageMapper;
    }

    @Override
    public ContactsPageResponse getContactsPageResponse() {
        logger.info("getContactsPageResponse() - Getting contacts page response");
        ContactsPage contactsPage = contactsPageRepository.findById(1L)
                .orElseThrow(()-> new EntityNotFoundException("Contacts page was not found by id 1"));
        ContactsPageResponse contactsPageResponse = contactsPageMapper
                .contactsPageToContactsPageResponse(contactsPage);
        logger.info("getContactsPageResponse() - Contacts page response has been got");
        return contactsPageResponse;
    }
}
