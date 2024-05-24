package com.example.admin.model.user;

import com.example.admin.entity.UserStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class UserDataImportRequest {
    @NotBlank(message = "Поле не може бути порожнім")
    @Size(max=50, message = "Розмір поля має бути не більше 50 символів")
    private String firstName;
    @NotBlank(message = "Поле не може бути порожнім")
    @Size(max=50, message = "Розмір поля має бути не більше 50 символів")
    private String lastName;
    @NotBlank(message = "Поле не може бути порожнім")
    @Size(max=50, message = "Розмір поля має бути не більше 50 символів")
    private String middleName;
    @NotBlank(message = "Поле не може бути порожнім")
    @Size(max=100, message = "Розмір поля має бути не більше 100 символів")
    @Pattern(regexp = "[a-zA-Z0-9._-]+@[a-zA-Z0-9-]+\\.[a-z]{2,3}(\\.[a-z]{2,3})?", message = "Пошта не відповідає формату")
    private String email;
    @NotBlank(message = "Поле не може бути порожнім")
    @Size(max = 13, message = "Розмір поля має бути не більше 13 символів")
    @Pattern(regexp = "\\+?380(50)?(66)?(95)?(99)?(67)?(68)?(96)?(97)?(98)?(63)?(93)?(73)?[0-9]{7}",
            message = "Номер телефону не відповідає формату '380991111111'")
    private String phoneNumber;
    @NotNull(message = "Поле не може бути порожнім")
    private Long apartmentNumber;
    @NotBlank(message = "Поле не може бути порожнім")
    @Pattern(regexp="[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{4}", message="Невірний формат рахунку")
    private String personalAccount;
    private UserStatus status;
    @NotNull(message = "Поле не може бути порожнім")
    private BigDecimal area;
    @NotBlank(message = "Поле не може бути порожнім")
    @Size(min = 10, max=100, message = "Довжина поля має бути від 10 до 100 символів")
    private String username;
    private String password;
    private HouseDataImportDto houseDataImportDto;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(Long apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public String getPersonalAccount() {
        return personalAccount;
    }

    public void setPersonalAccount(String personalAccount) {
        this.personalAccount = personalAccount;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HouseDataImportDto getHouseDataImportDto() {
        return houseDataImportDto;
    }

    public void setHouseDataImportDto(HouseDataImportDto houseDataImportDto) {
        this.houseDataImportDto = houseDataImportDto;
    }

    @Override
    public String toString() {
        return "UserDataImportRequest{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", apartmentNumber=" + apartmentNumber +
                ", personalAccount='" + personalAccount + '\'' +
                ", status=" + status +
                ", area=" + area +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
