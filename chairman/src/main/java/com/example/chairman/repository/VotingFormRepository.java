package com.example.chairman.repository;

import com.example.chairman.entity.VotingForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotingFormRepository extends JpaRepository<VotingForm, Long> {
}
