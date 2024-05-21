package com.example.chairman.repository;

import com.example.chairman.entity.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface HouseRepository extends JpaRepository<House, Long>, JpaSpecificationExecutor<House> {
}
