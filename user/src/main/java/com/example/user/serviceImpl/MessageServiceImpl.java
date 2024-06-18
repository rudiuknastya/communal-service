package com.example.user.serviceImpl;

import com.example.user.entity.Chairman;
import com.example.user.entity.Message;
import com.example.user.entity.User;
import com.example.user.mapper.MessageMapper;
import com.example.user.model.messages.MessageRequest;
import com.example.user.model.messages.ViewMessageResponse;
import com.example.user.repository.ChairmanRepository;
import com.example.user.repository.MessageRepository;
import com.example.user.repository.UserRepository;
import com.example.user.service.MessageService;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    private Message getMessageById(Long id){
        return messageRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Message was not found by id "+id));
    }
}
