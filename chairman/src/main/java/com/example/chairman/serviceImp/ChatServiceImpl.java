package com.example.chairman.serviceImp;

import com.example.chairman.entity.House;
import com.example.chairman.entity.User;
import com.example.chairman.mapper.ChatMapper;
import com.example.chairman.model.chairman.ChairmanDetails;
import com.example.chairman.model.chat.PageRequest;
import com.example.chairman.model.chat.UserResponse;
import com.example.chairman.repository.HouseRepository;
import com.example.chairman.repository.UserRepository;
import com.example.chairman.service.ChatService;
import com.example.chairman.specification.specificationFormer.UserSpecificationFormer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ChatServiceImpl implements ChatService {
    private final UserRepository userRepository;
    private final HouseRepository houseRepository;
    private final ChatMapper chatMapper;
    private final Logger logger = LogManager.getLogger(ChatServiceImpl.class);
    public ChatServiceImpl(UserRepository userRepository, HouseRepository houseRepository,
                           ChatMapper chatMapper) {
        this.userRepository = userRepository;
        this.houseRepository = houseRepository;
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

    private Long getAuthenticatedChairmanId(){
        ChairmanDetails chairmanDetails = (ChairmanDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return chairmanDetails.getId();
    }
}
