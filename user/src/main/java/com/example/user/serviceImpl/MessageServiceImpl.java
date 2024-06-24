package com.example.user.serviceImpl;

import com.example.user.entity.Chairman;
import com.example.user.entity.Message;
import com.example.user.entity.User;
import com.example.user.mapper.MessageMapper;
import com.example.user.model.messages.FilterRequest;
import com.example.user.model.messages.MessageRequest;
import com.example.user.model.messages.TableMessageResponse;
import com.example.user.model.messages.ViewMessageResponse;
import com.example.user.repository.ChairmanRepository;
import com.example.user.repository.MessageRepository;
import com.example.user.repository.UserRepository;
import com.example.user.service.MessageService;
import com.example.user.specification.specificationFormer.MessageSpecificationFormer;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final ChairmanRepository chairmanRepository;
    private final UserRepository userRepository;
    private final MessageMapper messageMapper;
    private final Logger logger = LogManager.getLogger(MessageServiceImpl.class);

    public MessageServiceImpl(MessageRepository messageRepository, ChairmanRepository chairmanRepository,
                              UserRepository userRepository, MessageMapper messageMapper) {
        this.messageRepository = messageRepository;
        this.chairmanRepository = chairmanRepository;
        this.userRepository = userRepository;
        this.messageMapper = messageMapper;
    }

    @Override
    public void createMessage(MessageRequest messageRequest) {
        logger.info("createMessage() - Creating message "+messageRequest.toString());
        Chairman chairman = chairmanRepository.findById(messageRequest.chairmanId())
                .orElseThrow(()-> new EntityNotFoundException("Chairman was not found by id "+messageRequest.chairmanId()));
        User user = getAuthenticatedUser();
        Message message = messageMapper
                .messageRequestToMessage(messageRequest, LocalDateTime.now(), user, chairman);
        messageRepository.save(message);
        logger.info("createMessage() - Message has been created");
    }
    private User getAuthenticatedUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsernameAndDeletedIsFalse(userDetails.getUsername()).orElseThrow(()-> new EntityNotFoundException("User was not found by username "+userDetails.getUsername()));
        return user;
    }

    @Override
    public ViewMessageResponse getViewMessageResponse(Long id) {
        logger.info("getViewMessageResponse() - Getting view message response by id "+id);
        Message message = getMessageById(id);
        ViewMessageResponse viewMessageResponse = messageMapper.messageToViewMessageResponse(message);
        logger.info("getViewMessageResponse() - View message response has been got");
        return viewMessageResponse;
    }

    @Override
    public void deleteMessage(Long id) {
        logger.info("deleteMessage() - Deleting message by id "+id);
        Message message = getMessageById(id);
        message.setDeleted(true);
        messageRepository.save(message);
        logger.info("deleteMessage() - Message has been deleted");
    }

    @Override
    public Page<TableMessageResponse> getMessageResponsesForTable(FilterRequest filterRequest) {
        logger.info("getMessageResponsesForTable() - Getting message responses for table "+filterRequest.toString());
        Pageable pageable = PageRequest.of(filterRequest.page(), filterRequest.pageSize());
        Page<Message> messagePage = getFilteredMessages(filterRequest, pageable);
        List<TableMessageResponse> tableMessageResponses = messageMapper
                .messageListToTableMessageResponseList(messagePage.getContent());
        Page<TableMessageResponse> messageResponsePage = new PageImpl<>(tableMessageResponses, pageable, messagePage.getTotalElements());
        logger.info("getMessageResponsesForTable() - Message responses have been got");
        return messageResponsePage;
    }
    private Page<Message> getFilteredMessages(FilterRequest filterRequest, Pageable pageable){
        Specification<Message> messageSpecification = MessageSpecificationFormer.formSpecification(filterRequest);
        return messageRepository.findAll(messageSpecification, pageable);
    }

    @Override
    public void deleteMessages(Long[] ids) {
        logger.info("deleteMessages() - Deleting messages by ids "+ids.toString());
        List<Message> messages = messageRepository.findAllById(Arrays.asList(ids));
        for(Message message: messages){
            message.setDeleted(true);
        }
        messageRepository.saveAll(messages);
        logger.info("deleteMessages() - Messages have been deleted");
    }

    private Message getMessageById(Long id){
        return messageRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Message was not found by id "+id));
    }
}
