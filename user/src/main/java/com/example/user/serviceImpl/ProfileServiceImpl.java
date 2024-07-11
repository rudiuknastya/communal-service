package com.example.user.serviceImpl;

import com.example.user.entity.User;
import com.example.user.mapper.UserMapper;
import com.example.user.model.user.MyUserDetails;
import com.example.user.model.user.ProfileResponse;
import com.example.user.repository.UserRepository;
import com.example.user.service.ProfileService;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final UserMapper userMapper;
    private final Logger logger = LogManager.getLogger(ProfileServiceImpl.class);


    public ProfileServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public ProfileResponse getProfileResponse() {
        logger.info("getProfileResponse() - Getting profile response");
        User user = getAuthenticatedUser();
        ProfileResponse profileResponse = userMapper.userToProfileResponse(user);
        logger.info("getProfileResponse() - Profile response has been got");
        return profileResponse;
    }

    private User getAuthenticatedUser(){
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return myUserDetails.getUser();
    }
}
