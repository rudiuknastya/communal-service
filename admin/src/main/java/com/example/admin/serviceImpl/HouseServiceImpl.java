package com.example.admin.serviceImpl;

import com.example.admin.entity.Chairman;
import com.example.admin.entity.House;
import com.example.admin.mapper.HouseMapper;
import com.example.admin.model.houses.HouseRequest;
import com.example.admin.repository.ChairmanRepository;
import com.example.admin.repository.HouseRepository;
import com.example.admin.service.HouseService;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class HouseServiceImpl implements HouseService {
    private final HouseRepository houseRepository;
    private final ChairmanRepository chairmanRepository;
    private final HouseMapper houseMapper;
    private final Logger logger = LogManager.getLogger(HouseServiceImpl.class);

    public HouseServiceImpl(HouseRepository houseRepository, ChairmanRepository chairmanRepository,
                            HouseMapper houseMapper) {
        this.houseRepository = houseRepository;
        this.chairmanRepository = chairmanRepository;
        this.houseMapper = houseMapper;
    }

    @Override
    public void createHouse(HouseRequest houseRequest) {
        logger.info("createHouse - Creating house "+houseRequest.toString());
        Chairman chairman = chairmanRepository.findById(houseRequest.chairmanId())
                .orElseThrow(()-> new EntityNotFoundException("Chairman was not found by id "+houseRequest.chairmanId()));
        House house = houseMapper.createHouse(houseRequest, chairman);
        houseRepository.save(house);
        logger.info("createHouse - House has been created");
    }
}
