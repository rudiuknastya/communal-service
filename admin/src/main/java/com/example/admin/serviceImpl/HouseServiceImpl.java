package com.example.admin.serviceImpl;

import com.example.admin.entity.Chairman;
import com.example.admin.entity.House;
import com.example.admin.mapper.HouseMapper;
import com.example.admin.model.general.SelectSearchRequest;
import com.example.admin.model.houses.*;
import com.example.admin.model.user.FilterHouseResponse;
import com.example.admin.repository.ChairmanRepository;
import com.example.admin.repository.HouseRepository;
import com.example.admin.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final HouseMapper houseMapper;
    private final HouseSpecificationFormer houseSpecificationFormer;
    private final Logger logger = LogManager.getLogger(HouseServiceImpl.class);

    public HouseServiceImpl(HouseRepository houseRepository, ChairmanRepository chairmanRepository,
                            UserRepository userRepository, HouseMapper houseMapper,
                            HouseSpecificationFormer houseSpecificationFormer) {
        this.houseRepository = houseRepository;
        this.chairmanRepository = chairmanRepository;
        this.userRepository = userRepository;
        this.houseMapper = houseMapper;
        this.houseSpecificationFormer = houseSpecificationFormer;
    }

    @Override
    public void createHouse(HouseRequest houseRequest) {
        logger.info("createHouse - Creating house "+ houseRequest.toString());
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
        Specification<House> houseSpecification = houseSpecificationFormer
                .formTableSpecification(filterRequest);
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
        logger.info("updateHouse - Updating house with id "+id+" "+houseRequest.toString());
        House house = getHouseById(id);
        Chairman chairman = getChairmanById(houseRequest.chairmanId());
        houseMapper.updateHouse(house, houseRequest, chairman);
        houseRepository.save(house);
        logger.info("updateHouse - House have been updated");
    }

    @Override
    public Page<String> getCities(SelectSearchRequest selectSearchRequest) {
        logger.info("getCities - Getting cities for select "+selectSearchRequest.toString());
        Pageable pageable = PageRequest.of(selectSearchRequest.page()-1, 10);
        Specification<House> houseSpecification = houseSpecificationFormer
                .formCitySelectSpecification(selectSearchRequest);
        Page<House> housePage = houseRepository.findAll(houseSpecification,pageable);
        List<String> cities = houseMapper.houseListToCityStringList(housePage.getContent());
        Page<String> citiesPage = new PageImpl<>(cities, pageable, housePage.getTotalElements());
        logger.info("getCities - Cities have been got");
        return citiesPage;
    }

    @Override
    public Page<String> getStreets(SelectSearchRequest selectSearchRequest, String city, String number) {
        logger.info("getStreets - Getting streets for select "+selectSearchRequest.toString()+" city: "+city+" number: "+number);
        Pageable pageable = PageRequest.of(selectSearchRequest.page()-1, 10);
        Specification<House> houseSpecification = houseSpecificationFormer
                .formStreetSelectSpecification(selectSearchRequest, city, number);
        Page<House> housePage = houseRepository.findAll(houseSpecification,pageable);
        List<String> streets = houseMapper.houseListToStreetStringList(housePage.getContent());
        Page<String> streetsPage = new PageImpl<>(streets, pageable, housePage.getTotalElements());
        logger.info("getStreets - Streets have been got");
        return streetsPage;
    }

    @Override
    public Page<HouseNumberResponse> getNumbers(SelectSearchRequest selectSearchRequest, String city, String street) {
        logger.info("getNumbers - Getting numbers for select "+selectSearchRequest.toString()+" city: "+city+" street: "+street);
        Pageable pageable = PageRequest.of(selectSearchRequest.page()-1, 10);
        Specification<House> houseSpecification = houseSpecificationFormer
                .formNumberSelectSpecification(selectSearchRequest, city, street);
        Page<House> housePage = houseRepository.findAll(houseSpecification, pageable);
        List<HouseNumberResponse> houseNumberResponses = houseMapper.houseListToHouseNumberResponseList(housePage.getContent());
        Page<HouseNumberResponse> houseNumberResponsePage = new PageImpl<>(houseNumberResponses, pageable, housePage.getTotalElements());
        logger.info("getNumbers - Numbers have been got");
        return houseNumberResponsePage;
    }

    @Override
    public void deleteHouse(Long id) {
        logger.info("deleteHouse - Deleting house by id "+id);
        House house = getHouseById(id);
        house.setDeleted(true);
        houseRepository.save(house);
        logger.info("deleteHouse - House have been deleted");
    }

    @Override
    public boolean checkIfPossibleToDelete(Long id) {
        logger.info("checkIfPossibleToDelete - Checking if possible to delete house by id "+id);
        int usersCount = userRepository.countUsersByHouseIdAndDeletedIsFalse(id);
        if(usersCount > 0) {
            logger.info("checkIfPossibleToDelete - Not possible to delete house");
            return false;
        }
        logger.info("checkIfPossibleToDelete - Possible to delete house");
        return true;
    }

    @Override
    public FilterHouseResponse getHouseResponseForUsersFilter(Long id) {
        logger.info("getHouseResponseForUsersFilter - Getting house response for users filter");
        House house = getHouseById(id);
        FilterHouseResponse filterHouseResponse = houseMapper.houseToFilterHouseResponse(house);
        logger.info("getHouseResponseForUsersFilter - House response have been got");
        return filterHouseResponse;
    }

    private House getHouseById(Long id){
        return houseRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("House was not found by id "+id));
    }
    private Chairman getChairmanById(Long id){
        return chairmanRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Chairman was not found by id "+id));
    }
}
