package com.example.admin.specification.specificationFormer;

import com.example.admin.entity.User;
import com.example.admin.model.user.FilterRequest;
import org.springframework.data.jpa.domain.Specification;

import static com.example.admin.specification.UserSpecification.*;

public class UserSpecificationFormer {
    public static Specification<User> formSpecification(FilterRequest filterRequest){
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
}
