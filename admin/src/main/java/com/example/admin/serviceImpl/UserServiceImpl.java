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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final HouseRepository houseRepository;
    private final UserMapper userMapper;
    private final UploadFileUtil uploadFileUtil;
    private final ExcelUploadUtil excelUploadUtil;
    private final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository, HouseRepository houseRepository,
                           UserMapper userMapper, UploadFileUtil uploadFileUtil,
                           ExcelUploadUtil excelUploadUtil) {
        this.userRepository = userRepository;
        this.houseRepository = houseRepository;
        this.userMapper = userMapper;
        this.uploadFileUtil = uploadFileUtil;
        this.excelUploadUtil = excelUploadUtil;
    }

    @Override
    public void createUser(CreateUserRequest createUserRequest) {
        logger.info("createUser - Creating user "+createUserRequest.toString());
        House house = getHouseById(createUserRequest.number());
        String avatar = saveNewAvatar(createUserRequest.avatar());
        User user = userMapper.createUser(createUserRequest, avatar, house);
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
        Specification<User> userSpecification = UserSpecificationFormer.formSpecification(filterRequest);
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
        User user = getUserById(id);
        House house = getHouseById(editUserRequest.number());
        String avatar = updateAvatar(editUserRequest.avatar(), user);
        userMapper.updateUser(user, editUserRequest, house, avatar);
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

    }
    private void openWorkBook(MultipartFile multipartFile) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(multipartFile.getInputStream());
        XSSFSheet sheet = workbook.getSheetAt(0);
        int rowIndex = 0;
        for(Row row: sheet){
            if(rowIndex == 0){
                rowIndex ++;
                continue;
            }
            Iterator<Cell> cellIterator = row.iterator();
            int cellIndex = 0;
            User user = new User();
            while (cellIterator.hasNext()){
                Cell cell = cellIterator.next();
                switch (cellIndex){
                    case 0 -> user.setLastName(cell.getStringCellValue());
                    case 1 -> user.setFirstName(cell.getStringCellValue());
                    case 2 -> user.setMiddleName(cell.getStringCellValue());
                    case 3 -> user.setPhoneNumber(cell.getStringCellValue());
                    case 4 -> user.setEmail(cell.getStringCellValue());
                }
            }
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
