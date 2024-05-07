package com.example.admin.serviceImpl;

import com.example.admin.entity.Chairman;
import com.example.admin.entity.House;
import com.example.admin.mapper.HouseMapper;
import com.example.admin.model.houses.FilterRequest;
import com.example.admin.model.houses.HouseRequest;
import com.example.admin.model.houses.HouseResponse;
import com.example.admin.model.houses.TableHouseResponse;
import com.example.admin.repository.ChairmanRepository;
import com.example.admin.repository.HouseRepository;
import com.example.admin.service.HouseService;
import com.example.admin.specification.specificationFormer.HouseSpecificationFormer;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Chairman chairman = getChairmanById(houseRequest.chairmanId());
        House house = houseMapper.createHouse(houseRequest, chairman);
        houseRepository.save(house);
        logger.info("createHouse - House has been created");
    }

    @Override
    public Page<TableHouseResponse> getHouseResponsesForTable(FilterRequest filterRequest) {
        logger.info("getHouseResponsesForTable - Getting house responses for table "+filterRequest.toString());
        Pageable pageable = PageRequest.of(filterRequest.page(), filterRequest.pageSize());
        Page<House> housePage = getFilteredHouses(filterRequest, pageable);
        List<TableHouseResponse> tableHouseResponses = houseMapper.houseListToTableHouseResponseList(housePage.getContent());
        Page<TableHouseResponse> tableHouseResponsePage = new PageImpl<>(tableHouseResponses, pageable, housePage.getTotalElements());
        logger.info("getHouseResponsesForTable - House responses have been got");
        return tableHouseResponsePage;
    }

    private Page<House> getFilteredHouses(FilterRequest filterRequest, Pageable pageable) {
        Specification<House> houseSpecification = HouseSpecificationFormer.formSpecification(filterRequest);
        return houseRepository.findAll(houseSpecification, pageable);
    }

    @Override
    public HouseResponse getHouseResponse(Long id) {
        logger.info("getHouseResponse - Getting house response by id "+id);
        House house = getHouseById(id);
        HouseResponse houseResponse = houseMapper.houseToHouseResponse(house);
        logger.info("getHouseResponse - House response have been got");
        return houseResponse;
    }

    @Override
    public void updateHouse(HouseRequest houseRequest, Long id) {
        House house = getHouseById(id);
        Chairman chairman = getChairmanById(houseRequest.chairmanId());
        houseMapper.updateHouse(house, houseRequest, chairman);
        houseRepository.save(house);
    }
    private House getHouseById(Long id){
        return houseRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("House was not found by id "+id));
    }
    private Chairman getChairmanById(Long id){
        return chairmanRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Chairman was not found by id "+id));
    }
}
