package com.example.user.repository;

import com.example.user.entity.VotingForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VotingFormRepository extends JpaRepository<VotingForm, Long>, JpaSpecificationExecutor<VotingForm> {

}
