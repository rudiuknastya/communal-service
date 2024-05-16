package com.example.admin.repository;

import com.example.admin.entity.Chairman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ChairmanRepository extends JpaRepository<Chairman, Long>, JpaSpecificationExecutor<Chairman> {
    boolean existsByEmailAndDeletedIsFalse(String email);

    boolean existsByEmailAndDeletedIsFalseAndIdNot(String email, Long id);

    boolean existsByPhoneNumberAndDeletedIsFalse(String phoneNumber);

    boolean existsByPhoneNumberAndDeletedIsFalseAndIdNot(String phoneNumber, Long id);

    boolean existsByUsernameAndDeletedIsFalse(String username);

    boolean existsByUsernameAndDeletedIsFalseAndIdNot(String username, Long id);
}
