package com.example.admin.repository;

import com.example.admin.entity.Chairman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ChairmanRepository extends JpaRepository<Chairman, Long>, JpaSpecificationExecutor<Chairman> {
    boolean existsByEmailAndDeleted(String email, boolean deleted);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByPhoneNumberAndDeleted(String phoneNumber, boolean deleted);

    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Long id);

    boolean existsByUsernameAndDeleted(String username, boolean deleted);

    boolean existsByUsernameAndIdNot(String username, Long id);
}
