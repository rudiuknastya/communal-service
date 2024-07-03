package com.example.chairman.repository;

import com.example.chairman.entity.RegistrationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RegistrationRequestRepository extends JpaRepository<RegistrationRequest, Long>, JpaSpecificationExecutor<RegistrationRequest> {
}
