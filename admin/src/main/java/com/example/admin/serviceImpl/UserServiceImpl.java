package com.example.admin.serviceImpl;

import com.example.admin.entity.House;
import com.example.admin.entity.User;
import com.example.admin.mapper.UserMapper;
import com.example.admin.model.user.*;
import com.example.admin.repository.HouseRepository;
import com.example.admin.repository.UserRepository;
import com.example.admin.service.UserService;
import com.example.admin.specification.specificationFormer.UserSpecificationFormer;
import com.example.admin.util.ExcelUploadUtil;
import com.example.admin.util.UploadFileUtil;
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

import java.io.IOException;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final HouseRepository houseRepository;
    private final UserMapper userMapper;
    private final UserSpecificationFormer userSpecificationFormer;
    private final UploadFileUtil uploadFileUtil;
    private final ExcelUploadUtil excelUploadUtil;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository, HouseRepository houseRepository,
                           UserMapper userMapper, UserSpecificationFormer userSpecificationFormer,
                           UploadFileUtil uploadFileUtil, ExcelUploadUtil excelUploadUtil,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.houseRepository = houseRepository;
        this.userMapper = userMapper;
        this.userSpecificationFormer = userSpecificationFormer;
        this.uploadFileUtil = uploadFileUtil;
        this.excelUploadUtil = excelUploadUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createUser(CreateUserRequest createUserRequest) {
        logger.info("createUser - Creating user "+createUserRequest.toString());
        House house = getHouseById(createUserRequest.number());
        String avatar = saveNewAvatar(createUserRequest.avatar());
        User user = userMapper.createUser(createUserRequest, avatar, house,
                passwordEncoder.encode(createUserRequest.password()));
        userRepository.save(user);
        logger.info("createUser - User has been created");
    }

    private String saveNewAvatar(MultipartFile avatar) {
        if(avatar.isEmpty()){
            return uploadFileUtil.saveDefaultAvatar();
        } else {
            return uploadFileUtil.saveMultipartFile(avatar);
        }
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
    public void deleteUser(Long id) {
        logger.info("deleteUser - Deleting user by id "+id);
        User user = getUserById(id);
        user.setDeleted(true);
        userRepository.save(user);
        logger.info("deleteUser - User have been deleted");
    }

    @Override
    public UserResponse getUserResponse(Long id) {
        logger.info("getUserResponse - Getting user response by id "+id);
        User user = getUserById(id);
        UserResponse userResponse = userMapper.userToUserResponse(user);
        logger.info("getUserResponse - User response have been got");
        return userResponse;
    }

    @Override
    public void updateUser(Long id, EditUserRequest editUserRequest) {
        logger.info("updateUser - Updating user with id "+id+" "+editUserRequest.toString());
        User user = getUserById(id);
        House house = getHouseById(editUserRequest.number());
        String avatar = updateAvatar(editUserRequest.avatar(), user);
        if (editUserRequest.password().isEmpty()) {
            userMapper.updateUserWithoutPassword(user, editUserRequest, house, avatar);
        } else {
            userMapper.updateUserWithPassword(user, editUserRequest, house, avatar,
                    passwordEncoder.encode(editUserRequest.password()));
        }
        logger.info("updateUser - User have been updated");
        userRepository.save(user);
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
    public void importDataFromXlsx(XlsxFileRequest xlsxFileRequest) {
        logger.info("importDataFromXlsx - Importing data from xlsx file "+xlsxFileRequest.xlsxFile().getOriginalFilename());
        try {
            List<UserDataImportRequest> userDataImportRequests =  excelUploadUtil.importDataFromExcelFile(xlsxFileRequest.xlsxFile());
            for(UserDataImportRequest userDataImportRequest: userDataImportRequests){
                HouseDataImportDto houseDataImportDto = userDataImportRequest.getHouseDataImportDto();
                House house = houseRepository.findByCityAndStreetAndNumberAndDeletedIsFalse(houseDataImportDto.getCity(),
                        houseDataImportDto.getStreet(), houseDataImportDto.getNumber())
                        .orElseThrow(()-> new EntityNotFoundException("House was not found by city: "+houseDataImportDto.getCity()+" street: "+houseDataImportDto.getStreet()+" number: "+houseDataImportDto.getNumber()));
                String avatar = uploadFileUtil.saveDefaultAvatar();
                User user = userMapper.userDataImportRequestToUser(userDataImportRequest,
                        house, passwordEncoder.encode(userDataImportRequest.getPassword()),
                        avatar);
                userRepository.save(user);
            }
            logger.info("importDataFromXlsx - Data has been imported");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("User was not found by id "+id));
    }
    private House getHouseById(Long id){
        return houseRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("House was not found by id "+id));
    }
}
