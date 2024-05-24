package com.example.chairman.serviceImp;

import com.example.chairman.entity.Message;
import com.example.chairman.mapper.MessageMapper;
import com.example.chairman.model.message.FilterRequest;
import com.example.chairman.model.message.TableMessageResponse;
import com.example.chairman.model.message.ViewMessageResponse;
import com.example.chairman.repository.MessageRepository;
import com.example.chairman.service.MessageService;
import com.example.chairman.specification.specificationFormer.MessageSpecificationFormer;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final Logger logger = LogManager.getLogger(MessageServiceImpl.class);

    public MessageServiceImpl(MessageRepository messageRepository, MessageMapper messageMapper) {
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
    }

    @Override
    public Page<TableMessageResponse> getMessageResponsesForTable(FilterRequest filterRequest) {
        logger.info("getMessageResponsesForTable - Getting message responses for table "+filterRequest.toString());
        Pageable pageable = PageRequest.of(filterRequest.page(), filterRequest.pageSize());
        Page<Message> messages = getFilteredMessages(filterRequest, pageable);
        List<TableMessageResponse> tableMessageResponses = messageMapper.messageListToTableMessageResponseList(messages.getContent());
        Page<TableMessageResponse> tableMessageResponsePage = new PageImpl<>(tableMessageResponses, pageable, messages.getTotalElements());
        logger.info("getMessageResponsesForTable - Message responses have been got");
        return tableMessageResponsePage;
    }

    private Page<Message> getFilteredMessages(FilterRequest filterRequest, Pageable pageable) {
        Specification<Message> messageSpecification = MessageSpecificationFormer.formSpecification(filterRequest);
        return messageRepository.findAll(messageSpecification, pageable);
    }

    @Override
    public ViewMessageResponse getViewMessageResponse(Long id) {
        logger.info("getViewMessageResponse - Getting view message response by id "+id);
        Message message = messageRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Message was not found by id "+id));
        ViewMessageResponse viewMessageResponse = messageMapper.messageToViewMessageResponse(message);
        logger.info("getViewMessageResponse - View message response has been got");
        return viewMessageResponse;
    }
}
