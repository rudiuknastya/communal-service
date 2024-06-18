package com.example.user.serviceImpl;

import com.example.user.entity.Chairman;
import com.example.user.mapper.ChairmanMapper;
import com.example.user.model.general.SelectSearchRequest;
import com.example.user.model.messages.ChairmanResponse;
import com.example.user.repository.ChairmanRepository;
import com.example.user.service.ChairmanService;
import com.example.user.specification.specificationFormer.ChairmanSpecificationFormer;
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
public class ChairmanServiceImpl implements ChairmanService {
    private final ChairmanRepository chairmanRepository;
    private final ChairmanMapper chairmanMapper;
    private final Logger logger = LogManager.getLogger(ChairmanServiceImpl.class);
    public ChairmanServiceImpl(ChairmanRepository chairmanRepository, ChairmanMapper chairmanMapper) {
        this.chairmanRepository = chairmanRepository;
        this.chairmanMapper = chairmanMapper;
    }

    @Override
    public Page<ChairmanResponse> getChairmanResponsesForSelect(SelectSearchRequest selectSearchRequest) {
        logger.info("getChairmanResponsesForSelect() - Getting chairman responses for select "+selectSearchRequest.toString());
        Pageable pageable = PageRequest.of(selectSearchRequest.page()-1, 10);
        Page<Chairman> chairmanPage = getFilteredChairmen(pageable, selectSearchRequest);
        List<ChairmanResponse> chairmanResponses = chairmanMapper.chairmanListToChairmanResponseList(chairmanPage.getContent());
        Page<ChairmanResponse> chairmanResponsePage = new PageImpl<>(chairmanResponses, pageable, chairmanPage.getTotalElements());
        logger.info("getChairmanResponsesForSelect() - Chairman responses have been got");
        return chairmanResponsePage;
    }
    private Page<Chairman> getFilteredChairmen(Pageable pageable, SelectSearchRequest selectSearchRequest){
        Specification<Chairman> chairmanSpecification = ChairmanSpecificationFormer.formSelectSpecification(selectSearchRequest);
        return chairmanRepository.findAll(chairmanSpecification, pageable);
    }
}
