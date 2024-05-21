package com.example.chairman.serviceImp;

import com.example.chairman.entity.ContactsPage;
import com.example.chairman.mapper.ContactsPageMapper;
import com.example.chairman.model.contactsPage.ContactsPageRequest;
import com.example.chairman.model.contactsPage.ContactsPageResponse;
import com.example.chairman.repository.ContactsPageRepository;
import com.example.chairman.service.ContactsPageService;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class ContactsPageServiceImpl implements ContactsPageService {
    private final ContactsPageRepository contactsPageRepository;
    private final ContactsPageMapper contactsPageMapper;
    private final Logger logger = LogManager.getLogger(ContactsPageServiceImpl.class);

    public ContactsPageServiceImpl(ContactsPageRepository contactsPageRepository,
                                   ContactsPageMapper contactsPageMapper) {
        this.contactsPageRepository = contactsPageRepository;
        this.contactsPageMapper = contactsPageMapper;
    }

    @Override
    public void createContactsPageIfNotExist() {
        logger.info("createContactsPageIfNotExist - Creating contacts page if not exist");
        if(isTableEmpty()){
            ContactsPage contactsPage = contactsPageMapper.createFirstContactsPage("");
            contactsPageRepository.save(contactsPage);
            logger.info("createContactsPageIfNotExist - Contacts page has been created");
        } else {
            logger.info("createContactsPageIfNotExist - Contacts page has already been created");
        }
    }

    private boolean isTableEmpty() {
        return contactsPageRepository.count() == 0;
    }

    @Override
    public ContactsPageResponse getContactsPageResponse() {
        logger.info("getContactsPageResponse - Getting contacts page response");
        ContactsPage contactsPage = getContactsPage();
        ContactsPageResponse contactsPageResponse = contactsPageMapper
                .contactsPageToContactsPageResponse(contactsPage);
        logger.info("getContactsPageResponse - Contacts page response has been got");
        return contactsPageResponse;
    }

    @Override
    public void updateContactsPage(ContactsPageRequest contactsPageRequest) {
        logger.info("updateContactsPage - Updating contacts page "+ contactsPageRequest.toString());
        ContactsPage contactsPage = getContactsPage();
        contactsPageMapper.updateContactsPage(contactsPage, contactsPageRequest);
        contactsPageRepository.save(contactsPage);
        logger.info("updateContactsPage - Contacts page has been updated");
    }

    private ContactsPage getContactsPage() {
        return contactsPageRepository.findById(1L).orElseThrow(()-> new EntityNotFoundException("Contacts page was not found by id 1"));
    }

}
