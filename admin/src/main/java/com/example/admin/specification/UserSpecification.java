package com.example.admin.specification;

import com.example.admin.entity.House;
import com.example.admin.entity.User;
import com.example.admin.entity.UserStatus;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public interface UserSpecification {
    static Specification<User> byDeleted(){
        return (root, query, builder) ->
                builder.equal(root.get("deleted"), false);
    }
    static Specification<User> byFirstName(String firstName){
        return (root, query, builder) ->
                builder.like(builder.upper(root.get("firstName")), "%"+firstName.toUpperCase()+"%");
    }
    static Specification<User> byLastName(String lastName){
        return (root, query, builder) ->
                builder.like(builder.upper(root.get("lastName")), "%"+lastName.toUpperCase()+"%");
    }
    static Specification<User> byMiddleName(String middleName){
        return (root, query, builder) ->
                builder.like(builder.upper(root.get("middleName")), "%"+middleName.toUpperCase()+"%");
    }
    static Specification<User> byHouseCity(String city){
        return (root, query, builder) -> {
            Join<User, House> houseJoin = root.join("house");
            return builder.equal(houseJoin.get("city"), city);
        };
    }
    static Specification<User> byHouseStreet(String street){
        return (root, query, builder) -> {
            Join<User, House> houseJoin = root.join("house");
            return builder.equal(houseJoin.get("street"), street);
        };
    }
    static Specification<User> byHouseNumber(Long houseNumber){
        return (root, query, builder) -> {
            Join<User, House> houseJoin = root.join("house");
            return builder.equal(houseJoin.get("number"), houseNumber);
        };
    }
    static Specification<User> byApartmentNumber(Long apartmentNumber){
        return (root, query, builder) ->
                builder.equal(root.get("apartmentNumber"), apartmentNumber);
    }
    static Specification<User> byPersonalAccount(String personalAccount){
        return (root, query, builder) ->
                builder.like(builder.upper(root.get("personalAccount")), "%"+personalAccount.toUpperCase()+"%");
    }
    static Specification<User> byPhoneNumber(String phoneNumber){
        return (root, query, builder) ->
                builder.like(builder.upper(root.get("phoneNumber")), "%"+phoneNumber.toUpperCase()+"%");
    }
    static Specification<User> byStatus(UserStatus status){
        return (root, query, builder) ->
                builder.equal(root.get("status"), status);
    }
}
