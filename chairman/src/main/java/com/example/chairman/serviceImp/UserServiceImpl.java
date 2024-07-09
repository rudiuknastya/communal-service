package com.example.chairman.serviceImp;

import com.example.chairman.entity.User;
import com.example.chairman.mapper.UserMapper;
import com.example.chairman.model.general.SelectSearchRequest;
import com.example.chairman.model.invoice.UserNameResponse;
import com.example.chairman.model.user.FilterRequest;
import com.example.chairman.model.user.TableUserResponse;
import com.example.chairman.model.user.UserRequest;
import com.example.chairman.model.user.UserResponse;
import com.example.chairman.repository.UserRepository;
import com.example.chairman.service.UserService;
import com.example.chairman.specification.specificationFormer.UserSpecificationFormer;
import com.example.chairman.util.UploadFileUtil;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UploadFileUtil uploadFileUtil;
    private final UserSpecificationFormer userSpecificationFormer;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper,
                           UploadFileUtil uploadFileUtil, UserSpecificationFormer userSpecificationFormer,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.uploadFileUtil = uploadFileUtil;
        this.userSpecificationFormer = userSpecificationFormer;
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

    @Override
    public Page<TableUserResponse> getUserResponsesForTable(FilterRequest filterRequest) {
        logger.info("getUserResponsesForTable - Getting user responses for table "+filterRequest.toString());
        Pageable pageable = PageRequest.of(filterRequest.page(), filterRequest.pageSize());
        Page<User> userPage = getFilteredUsers(filterRequest, pageable);
        List<TableUserResponse> tableUserResponses = userMapper.userListToTableUserResponseList(userPage.getContent());
        Page<TableUserResponse> tableUserResponsePage = new PageImpl<>(tableUserResponses, pageable, userPage.getTotalElements());
        logger.info("getUserResponsesForTable - User responses have been got");
        return tableUserResponsePage;
    }
    private Page<User> getFilteredUsers(FilterRequest filterRequest, Pageable pageable) {
        Specification<User> userSpecification = userSpecificationFormer.formTableSpecification(filterRequest);
        return userRepository.findAll(userSpecification, pageable);
    }

    @Override
    public Page<UserNameResponse> getUserNameResponses(SelectSearchRequest selectSearchRequest) {
        logger.info("getUserNameResponses - Getting user name responses for select "+selectSearchRequest.toString());
        Pageable pageable = PageRequest.of(selectSearchRequest.page()-1, 10);
        Page<User> users = getFilteredUsersForSelect(selectSearchRequest, pageable);
        List<UserNameResponse> userNameResponses = userMapper.userListToUserNameResponseList(users.getContent());
        Page<UserNameResponse> userNameResponsePage = new PageImpl<>(userNameResponses, pageable, users.getTotalElements());
        logger.info("getUserNameResponses - User name responses have been got");
        return userNameResponsePage;
    }

    private Page<User> getFilteredUsersForSelect(SelectSearchRequest selectSearchRequest, Pageable pageable) {
        Specification<User> userSpecification = UserSpecificationFormer.formSelectSpecification(selectSearchRequest);
        return userRepository.findAll(userSpecification, pageable);
    }

    @Override
    public String getPersonalAccount(Long id) {
        logger.info("getPersonalAccount - Getting personal account");
        User user = getUserById(id);
        String personalAccount = user.getPersonalAccount();
        logger.info("getPersonalAccount - Personal account has been got");
        return personalAccount;
    }

    private User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("User was not found by id "+id));
    }
}
