package com.example.admin.repository;

import com.example.admin.entity.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface HouseRepository extends JpaRepository<House, Long>, JpaSpecificationExecutor<House> {
    int countHousesByChairmanIdAndDeletedIsFalse(Long id);
    boolean existsByCityAndStreetAndNumberAndDeletedIsFalse(String city, String street, String number);
    boolean existsByCityAndStreetAndNumberAndDeletedIsFalseAndIdNot(String city, String street, String number, Long id);
}
