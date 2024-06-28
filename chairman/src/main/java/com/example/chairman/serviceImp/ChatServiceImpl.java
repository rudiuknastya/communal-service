package com.example.chairman.serviceImp;

import com.example.chairman.entity.Chairman;
import com.example.chairman.entity.Chat;
import com.example.chairman.entity.House;
import com.example.chairman.entity.User;
import com.example.chairman.mapper.ChatMapper;
import com.example.chairman.model.chairman.ChairmanDetails;
import com.example.chairman.model.chat.PageRequest;
import com.example.chairman.model.chat.UserResponse;
import com.example.chairman.repository.ChairmanRepository;
import com.example.chairman.repository.ChatRepository;
import com.example.chairman.repository.HouseRepository;
import com.example.chairman.repository.UserRepository;
import com.example.chairman.service.ChatService;
import com.example.chairman.specification.specificationFormer.UserSpecificationFormer;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatServiceImpl implements ChatService {
    private final UserRepository userRepository;
    private final ChairmanRepository chairmanRepository;
    private final HouseRepository houseRepository;
    private final ChatRepository chatRepository;
    private final ChatMapper chatMapper;
    private final Logger logger = LogManager.getLogger(ChatServiceImpl.class);

    public ChatServiceImpl(UserRepository userRepository, ChairmanRepository chairmanRepository, HouseRepository houseRepository,
                           ChatRepository chatRepository, ChatMapper chatMapper) {
        this.userRepository = userRepository;
        this.chairmanRepository = chairmanRepository;
        this.houseRepository = houseRepository;
        this.chatRepository = chatRepository;
        this.chatMapper = chatMapper;
    }

    @Override
    public Page<UserResponse> getUsersForChat(PageRequest pageRequest) {
        logger.info("getUsersForChat - Getting users for chat "+pageRequest.toString());
        Pageable pageable = org.springframework.data.domain.PageRequest.of(pageRequest.page(), pageRequest.pageSize());
        Long chairmanId = getAuthenticatedChairmanId();
        List<House> houses = houseRepository.findByChairmanId(chairmanId);
        Page<User> userPage = getFilteredUsers(houses, pageable);
        List<UserResponse> userResponses = chatMapper.userListToUserResponseList(userPage.getContent());
        Page<UserResponse> userResponsePage = new PageImpl<>(userResponses, pageable, userPage.getTotalElements());
        logger.info("getUsersForChat - Users have been got");
        return userResponsePage;
    }

    private Page<User> getFilteredUsers(List<House> houses, Pageable pageable) {
        Specification<User> userSpecification = UserSpecificationFormer.formChatSpecification(houses);
        return userRepository.findAll(userSpecification, pageable);
    }
    @Override
    public Chat getChat(Long chairmanId, Long userId){
        Optional<Chat> chatOptional = chatRepository.findByChairmanIdAndUserId(chairmanId, userId);
        return chatOptional.orElseGet(() -> createChat(chairmanId, userId));
    }
    private Chat createChat(Long chairmanId, Long userId){
        Chairman chairman = chairmanRepository.findById(chairmanId)
                .orElseThrow(()-> new EntityNotFoundException("Chairman was not found by id "+chairmanId));
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new EntityNotFoundException("User was not found by id "+userId));
        Chat chat = chatMapper.createChat(chairman, user);
        Chat savedChat = chatRepository.save(chat);
        return savedChat;
    }
    private Long getAuthenticatedChairmanId(){
        ChairmanDetails chairmanDetails = (ChairmanDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return chairmanDetails.getId();
    }
}
