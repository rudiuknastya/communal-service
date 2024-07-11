package com.example.user.serviceImpl;

import com.example.user.entity.House;
import com.example.user.entity.User;
import com.example.user.entity.enums.UserStatus;
import com.example.user.repository.HouseRepository;
import com.example.user.mapper.UserMapper;
import com.example.user.model.authentication.RegisterRequest;
import com.example.user.repository.UserRepository;
import com.example.user.service.RegistrationRequestService;
import com.example.user.service.UserService;
import com.example.user.util.UploadFileUtil;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final HouseRepository houseRepository;
    private final RegistrationRequestService registrationRequestService;
    private final PasswordEncoder passwordEncoder;
    private final UploadFileUtil uploadFileUtil;
    private final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper,
                           HouseRepository houseRepository, RegistrationRequestService registrationRequestService,
                           PasswordEncoder passwordEncoder, UploadFileUtil uploadFileUtil) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.houseRepository = houseRepository;
        this.registrationRequestService = registrationRequestService;
        this.passwordEncoder = passwordEncoder;
        this.uploadFileUtil = uploadFileUtil;
    }

    @Override
    public void register(RegisterRequest registerRequest) {
        logger.info("register - Registering user "+registerRequest.toString());
        House house = houseRepository.findById(registerRequest.houseId()).orElseThrow(()-> new EntityNotFoundException("House was not found by id "+registerRequest.houseId()));
        String avatar = uploadFileUtil.saveDefaultAvatar();
        User user = userMapper.registerRequestToUser(registerRequest, house, UserStatus.NEW,
                avatar, passwordEncoder.encode(registerRequest.password()));
        User savedUser = userRepository.save(user);
        registrationRequestService.createRegistrationRequest(savedUser, house.getChairman());
        logger.info("register - User has been registered");
    }
}
