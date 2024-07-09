package com.example.chairman.specification.specificationFormer;


import com.example.chairman.entity.House;
import com.example.chairman.entity.User;
import com.example.chairman.model.general.SelectSearchRequest;
import com.example.chairman.model.user.FilterRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.chairman.specification.UserSpecification.*;
@Component
public class UserSpecificationFormer {
    public Specification<User> formTableSpecification(FilterRequest filterRequest){
        Specification<User> userSpecification = Specification.where(byDeleted());
        if(!filterRequest.fullName().isEmpty()){
            String[] fullName = filterRequest.fullName().split(" ");
            if(fullName.length == 1 || fullName.length == 3) {
                userSpecification = userSpecification.and(byLastName(fullName[0]));
            }
            if (fullName.length == 2 || fullName.length == 3) {
                userSpecification = userSpecification.and(byFirstName(fullName[1]));
            }
            if (fullName.length == 3) {
                userSpecification = userSpecification.and(byMiddleName(fullName[2]));
            }
        }
        if(!filterRequest.city().isEmpty()){
            userSpecification = userSpecification.and(byHouseCity(filterRequest.city()));
        }
        if (!filterRequest.street().isEmpty()){
            userSpecification = userSpecification.and(byHouseStreet(filterRequest.street()));
        }
        if (filterRequest.houseNumber() != null){
            userSpecification = userSpecification.and(byHouseNumber(filterRequest.houseNumber()));
        }
        if (filterRequest.apartmentNumber() != null){
            userSpecification = userSpecification.and(byApartmentNumber(filterRequest.apartmentNumber()));
        }
        if (!filterRequest.personalAccount().isEmpty()){
            userSpecification = userSpecification.and(byPersonalAccount(filterRequest.personalAccount()));
        }
        if (!filterRequest.phoneNumber().isEmpty()){
            userSpecification = userSpecification.and(byPhoneNumber(filterRequest.phoneNumber()));
        }
        if (filterRequest.status() != null){
            userSpecification = userSpecification.and(byStatus(filterRequest.status()));
        }
        return userSpecification;
    }

    public static Specification<User> formSelectSpecification(SelectSearchRequest selectSearchRequest){
        Specification<User> userSpecification = Specification.where(byDeleted());
        if(!selectSearchRequest.search().isEmpty()){
            String[] fullName = selectSearchRequest.search().split(" ");
            if(fullName.length == 1 || fullName.length == 3) {
                userSpecification = userSpecification.and(byLastName(fullName[0]));
            }
            if (fullName.length == 2 || fullName.length == 3) {
                userSpecification = userSpecification.and(byFirstName(fullName[1]));
            }
            if (fullName.length == 3) {
                userSpecification = userSpecification.and(byMiddleName(fullName[2]));
            }
        }
        return userSpecification;
    }
    public static Specification<User> formChatSpecification(List<House> houses){
        Specification<User> userSpecification = Specification.where(byDeleted());
        for(House house : houses){
            userSpecification = userSpecification.and(byHouse(house));
        }
        return userSpecification;
    }
}
