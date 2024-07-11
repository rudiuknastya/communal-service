package com.example.user.serviceImpl;

import com.example.user.entity.House;
import com.example.user.mapper.HouseMapper;
import com.example.user.model.house.HouseNumberResponse;
import com.example.user.repository.HouseRepository;
import com.example.user.model.general.SelectSearchRequest;
import com.example.user.service.HouseService;
import com.example.user.specification.specificationFormer.HouseSpecificationFormer;
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
    private final HouseMapper houseMapper;
    private final HouseSpecificationFormer houseSpecificationFormer;
    private final Logger logger = LogManager.getLogger(HouseServiceImpl.class);

    public HouseServiceImpl(HouseRepository houseRepository, HouseMapper houseMapper,
                            HouseSpecificationFormer houseSpecificationFormer) {
        this.houseRepository = houseRepository;
        this.houseMapper = houseMapper;
        this.houseSpecificationFormer = houseSpecificationFormer;
    }

    @Override
    public Page<String> getCitiesForSelect(SelectSearchRequest selectSearchRequest) {
        logger.info("getCitiesForSelect - Getting cities for select "+selectSearchRequest.toString());
        Pageable pageable = PageRequest.of(selectSearchRequest.page()-1, 10);
        Specification<House> houseSpecification = houseSpecificationFormer
                .formCitySelectSpecification(selectSearchRequest);
        Page<House> housePage = houseRepository.findAll(houseSpecification, pageable);
        List<String> cities = houseMapper.houseListToCityStringList(housePage.getContent());
        Page<String> cityPage = new PageImpl<>(cities, pageable, housePage.getTotalElements());
        logger.info("getCitiesForSelect - Cities have been got");
        return cityPage;
    }

    @Override
    public Page<String> getStreetsForSelect(SelectSearchRequest selectSearchRequest, String city, String number) {
        logger.info("getStreetsForSelect - Getting streets for select "+selectSearchRequest.toString()+" city: "+city+" number: "+number);
        Pageable pageable = PageRequest.of(selectSearchRequest.page()-1, 10);
        Specification<House> houseSpecification = houseSpecificationFormer
                .formStreetSelectSpecification(selectSearchRequest, city, number);
        Page<House> housePage = houseRepository.findAll(houseSpecification,pageable);
        List<String> streets = houseMapper.houseListToStreetStringList(housePage.getContent());
        Page<String> streetsPage = new PageImpl<>(streets, pageable, housePage.getTotalElements());
        logger.info("getStreetsForSelect - Streets have been got");
        return streetsPage;
    }

    @Override
    public Page<HouseNumberResponse> getNumbersForSelect(SelectSearchRequest selectSearchRequest, String city, String street) {
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
}
