package com.example.chairman.serviceImp;

import com.example.chairman.entity.User;
import com.example.chairman.mapper.UserMapper;
import com.example.chairman.model.user.UserRequest;
import com.example.chairman.model.user.UserResponse;
import com.example.chairman.repository.UserRepository;
import com.example.chairman.service.UserService;
import com.example.chairman.util.UploadFileUtil;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UploadFileUtil uploadFileUtil;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper,
                           UploadFileUtil uploadFileUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.uploadFileUtil = uploadFileUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void updateUser(UserRequest userRequest, Long id) {
        logger.info("updateUser - Updating user by id "+id+" "+ userRequest.toString());
        User user = getUserById(id);
        String avatar = updateAvatar(userRequest.avatar(), user);
        if (userRequest.password().isEmpty()) {
            userMapper.updateUserWithoutPassword(user, userRequest, avatar);
        } else {
            userMapper.updateUserWithPassword(user, userRequest, avatar,
                    passwordEncoder.encode(userRequest.password()));
        }
        userRepository.save(user);
        logger.info("updateUser - User has been updated");
    }
    private String updateAvatar(MultipartFile avatar, User user) {
        String currentAvatar = user.getAvatar();
        if(avatar.isEmpty()){
            return currentAvatar;
        } else {
            uploadFileUtil.deleteFile(currentAvatar);
            return uploadFileUtil.saveMultipartFile(avatar);
        }
    }
    @Override
    public UserResponse getUserResponse(Long id) {
        logger.info("getUserResponse - Getting user response by id "+id);
        User user = getUserById(id);
        UserResponse userResponse = userMapper.userToUserResponse(user);
        logger.info("getUserResponse - User response has been got");
        return userResponse;
    }
    private User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("User was not found by id "+id));
    }
}
