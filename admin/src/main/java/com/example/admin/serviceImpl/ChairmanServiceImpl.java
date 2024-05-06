package com.example.admin.serviceImpl;

import com.example.admin.entity.Chairman;
import com.example.admin.mapper.ChairmanMapper;
import com.example.admin.model.chairmen.*;
import com.example.admin.model.general.SelectSearchRequest;
import com.example.admin.model.houses.ChairmanNameResponse;
import com.example.admin.repository.ChairmanRepository;
import com.example.admin.service.ChairmanService;
import com.example.admin.specification.specificationFormer.ChairmanSpecificationFormer;
import com.example.admin.util.UploadFileUtil;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.example.admin.specification.ChairmanSpecification.byDeleted;

@Service
public class ChairmanServiceImpl implements ChairmanService {
    private final ChairmanRepository chairmanRepository;
    private final ChairmanMapper chairmanMapper;
    private final UploadFileUtil uploadFileUtil;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = LogManager.getLogger(ChairmanServiceImpl.class);

    public ChairmanServiceImpl(ChairmanRepository chairmanRepository, ChairmanMapper chairmanMapper,
                               UploadFileUtil uploadFileUtil, PasswordEncoder passwordEncoder) {
        this.chairmanRepository = chairmanRepository;
        this.chairmanMapper = chairmanMapper;
        this.uploadFileUtil = uploadFileUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createChairman(CreateChairmanRequest createChairmanRequest) {
        logger.info("createChairman - Creating chairman "+createChairmanRequest.toString());
        String avatar = saveNewAvatar(createChairmanRequest.avatar());
        Chairman chairman = chairmanMapper.createChairman(createChairmanRequest, avatar,
                passwordEncoder.encode(createChairmanRequest.password()));
        saveChairman(chairman);
        logger.info("createChairman - Chairman has been created");
    }
    private String saveNewAvatar(MultipartFile avatar){
        if(avatar.isEmpty()){
            return uploadFileUtil.saveDefaultAvatar();
        } else {
            return uploadFileUtil.saveMultipartFile(avatar);
        }
    }

    @Override
    public Page<TableChairmanResponse> getChairmenForTable(FilterRequest filterRequest) {
        logger.info("getChairmenForTable - Getting chairmen for table with filter "+filterRequest.toString());
        Pageable pageable = PageRequest.of(filterRequest.page(), filterRequest.pageSize());
        Page<Chairman> chairmen = getFilteredChairmen(filterRequest, pageable);
        List<TableChairmanResponse> tableChairmanResponses = chairmanMapper
                .chairmanListToTableChairmanResponseList(chairmen.getContent());
        Page<TableChairmanResponse> tableChairmanResponsePage = new PageImpl<>(tableChairmanResponses, pageable, chairmen.getTotalElements());
        logger.info("getChairmenForTable - Chairmen have been got");
        return tableChairmanResponsePage;
    }
    private Page<Chairman> getFilteredChairmen(FilterRequest filterRequest, Pageable pageable){
        Specification<Chairman> specification = ChairmanSpecificationFormer.formSpecification(filterRequest);
        return chairmanRepository.findAll(specification, pageable);
    }

    @Override
    public boolean deleteChairman(Long id) {
        Chairman chairman = getChairmanById(id);
        // todo check if has houses
        return false;
    }

    @Override
    public ChairmanResponse getChairmanResponse(Long id) {
        logger.info("getChairmanResponse - Getting chairman response by id "+id);
        Chairman chairman = getChairmanById(id);
        ChairmanResponse chairmanResponse = chairmanMapper.chairmanToChairmanResponse(chairman);
        logger.info("getChairmanResponse - Chairman response has been got");
        return chairmanResponse;
    }

    @Override
    public void updateChairman(EditChairmanRequest editChairmanRequest, Long id) {
        logger.info("updateChairman - Updating chairman with id "+id+ " "+editChairmanRequest.toString());
        Chairman chairman = getChairmanById(id);
        String avatar = updateAvatar(editChairmanRequest.avatar(), chairman);
        if(editChairmanRequest.password().isEmpty()){
            chairmanMapper.updateChairmanWithoutPassword(chairman,avatar, editChairmanRequest);
        } else {
            chairmanMapper.updateChairmanWithPassword(chairman, avatar, editChairmanRequest,
                    passwordEncoder.encode(editChairmanRequest.password()));
        }
        saveChairman(chairman);
        logger.info("updateChairman - Chairman was updated");
    }

    private String updateAvatar(MultipartFile avatar, Chairman chairman) {
        String currentAvatar = chairman.getAvatar();
        if(avatar.isEmpty()){
            return currentAvatar;
        } else {
            uploadFileUtil.deleteFile(currentAvatar);
            return uploadFileUtil.saveMultipartFile(avatar);
        }
    }

    @Override
    public Page<ChairmanNameResponse> getChairmanNameResponses(SelectSearchRequest selectSearchRequest) {
        logger.info("getChairmanNameResponses - Getting chairman name responses "+selectSearchRequest.toString());
        Pageable pageable = PageRequest.of(selectSearchRequest.page()-1, 10);
        Page<Chairman> chairmen = chairmanRepository.findAll(ChairmanSpecificationFormer.formSelectSpecification(selectSearchRequest), pageable);
        List<ChairmanNameResponse> chairmanNameResponses = chairmanMapper.chairmanListToChairmanNameResponseList(chairmen.getContent());
        Page<ChairmanNameResponse> chairmanNameResponsePage = new PageImpl<>(chairmanNameResponses, pageable, chairmen.getTotalElements());
        logger.info("getChairmanNameResponses - Chairman name responses have been got");
        return chairmanNameResponsePage;
    }

    private Chairman getChairmanById(Long id){
        return chairmanRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Chairman was not found by id "+id));
    }

    private void saveChairman(Chairman chairman){
        chairmanRepository.save(chairman);
    }
}
