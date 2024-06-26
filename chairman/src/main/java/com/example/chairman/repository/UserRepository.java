package com.example.chairman.repository;


import com.example.chairman.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    boolean existsByEmailAndDeletedIsFalseAndIdNot(String email, Long id);
    boolean existsByPhoneNumberAndDeletedIsFalseAndIdNot(String phoneNumber, Long id);
    boolean existsByUsernameAndDeletedIsFalseAndIdNot(String username, Long id);
}
