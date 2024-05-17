package com.example.admin.util;

import com.example.admin.entity.UserStatus;
import com.example.admin.model.user.HouseDataImportDto;
import com.example.admin.model.user.UserDataImportRequest;
import com.example.admin.validation.user.UserDataImportValidator;
import jakarta.validation.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Component
public class ExcelUploadUtil {
    private final UserDataImportValidator userDataImportValidator;

    public ExcelUploadUtil(UserDataImportValidator userDataImportValidator) {
        this.userDataImportValidator = userDataImportValidator;
    }

    public List<UserDataImportRequest> importDataFromExcelFile(MultipartFile file) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        XSSFSheet sheet = workbook.getSheetAt(0);
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        List<UserDataImportRequest> userDataImportRequests = new ArrayList<>();
        int rowIndex = 0;
        for(Row row: sheet){
            if(rowIndex == 0){
                rowIndex ++;
                continue;
            }
            Iterator<Cell> cellIterator = row.iterator();
            int cellIndex = 0;
            UserDataImportRequest importRequest = new UserDataImportRequest();
            HouseDataImportDto houseDataImportDto = new HouseDataImportDto();
            while (cellIterator.hasNext()){
                Cell cell = cellIterator.next();
                setCellData(cell, cellIndex, importRequest, houseDataImportDto);
                cellIndex ++;
            }
            validateData(importRequest, houseDataImportDto, validator, rowIndex);
            importRequest.setHouseDataImportDto(houseDataImportDto);
            userDataImportRequests.add(importRequest);
            rowIndex++;
        }
        return userDataImportRequests;
    }

    private void setCellData(Cell cell, int cellIndex, UserDataImportRequest importRequest, HouseDataImportDto houseDataImportDto) {
        switch (cellIndex){
            case 0:
                importRequest.setLastName(cell.getStringCellValue());
                break;
            case 1:
                importRequest.setFirstName(cell.getStringCellValue());
                break;
            case 2:
                importRequest.setMiddleName(cell.getStringCellValue());
                break;
            case 3:
                importRequest.setPhoneNumber("+"+cell.getStringCellValue());
                break;
            case 4:
                importRequest.setEmail(cell.getStringCellValue());
                break;
            case 5:
                houseDataImportDto.setCity(cell.getStringCellValue());
                break;
            case 6:
                houseDataImportDto.setStreet(cell.getStringCellValue());
                break;
            case 7:
                houseDataImportDto.setNumber(cell.getStringCellValue());
                break;
            case 8:
                importRequest.setPersonalAccount(cell.getStringCellValue());
                break;
            case 9:
                importRequest.setStatus(UserStatus.valueOf(cell.getStringCellValue()));
                break;
            case 10:
                importRequest.setApartmentNumber((long) cell.getNumericCellValue());
                break;
            case 11:
                importRequest.setArea(BigDecimal.valueOf(cell.getNumericCellValue()));
                break;
            case 12:
                importRequest.setUsername(cell.getStringCellValue());
                break;
            case 13:
                importRequest.setPassword(cell.getStringCellValue());
                break;
        }
    }

    private void validateData(UserDataImportRequest importRequest, HouseDataImportDto houseDataImportDto, Validator validator, int rowIndex) {
        Set<ConstraintViolation<UserDataImportRequest>> violations = validator.validate(importRequest);
        Set<ConstraintViolation<UserDataImportRequest>> mutableViolations = new HashSet<>(violations);
        userDataImportValidator.validateHouseExist(houseDataImportDto, mutableViolations);
        userDataImportValidator.validateForUniqueness(importRequest, mutableViolations);
        if (!mutableViolations.isEmpty()){
            Path path = PathImpl.createPathFromString("row");
            ConstraintViolation<UserDataImportRequest> violation =  ConstraintViolationImpl.forBeanValidation(String.valueOf(rowIndex+1), null, null, String.valueOf(rowIndex+1), UserDataImportRequest.class, new UserDataImportRequest(), null, null, path, null, null);
            mutableViolations.add(violation);
            throw new ConstraintViolationException(mutableViolations);
        }
    }
}
