package com.example.admin.repository;

import com.example.admin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    boolean existsByPhoneNumberAndDeletedIsFalse(String phoneNumber);
    boolean existsByEmailAndDeletedIsFalse(String email);
    boolean existsByUsernameAndDeletedIsFalse(String username);
    boolean existsByPersonalAccountAndDeletedIsFalse(String personalAccount);
    boolean existsByEmailAndDeletedIsFalseAndIdNot(String email, Long id);
    boolean existsByPhoneNumberAndDeletedIsFalseAndIdNot(String phoneNumber, Long id);
    boolean existsByPersonalAccountAndDeletedIsFalseAndIdNot(String personalAccount, Long id);
    boolean existsByUsernameAndDeletedIsFalseAndIdNot(String username, Long id);
    int countUsersByHouseIdAndDeletedIsFalse(Long houseId);
}
