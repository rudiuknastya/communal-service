package com.example.chairman.serviceImp;

import com.example.chairman.entity.House;
import com.example.chairman.mapper.HouseMapper;
import com.example.chairman.model.general.SelectSearchRequest;
import com.example.chairman.repository.HouseRepository;
import com.example.chairman.service.HouseService;
import com.example.chairman.specification.specificationFormer.HouseSpecificationFormer;
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
    public Page<String> getNumbers(SelectSearchRequest selectSearchRequest, String city, String street) {
        logger.info("getNumbers - Getting numbers for select "+selectSearchRequest.toString()+" city: "+city+" street: "+street);
        Pageable pageable = PageRequest.of(selectSearchRequest.page()-1, 10);
        Specification<House> houseSpecification = houseSpecificationFormer
                .formNumberSelectSpecification(selectSearchRequest, city, street);
        Page<House> housePage = houseRepository.findAll(houseSpecification, pageable);
        List<String> houseNumberResponses = houseMapper.houseListToNumberStringList(housePage.getContent());
        Page<String> houseNumberResponsePage = new PageImpl<>(houseNumberResponses, pageable, housePage.getTotalElements());
        logger.info("getNumbers - Numbers have been got");
        return houseNumberResponsePage;
    }
}
