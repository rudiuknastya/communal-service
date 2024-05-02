package com.example.admin.serviceImpl;

import com.example.admin.entity.Chairman;
import com.example.admin.mapper.ChairmanMapper;
import com.example.admin.model.chairmen.CreateChairmanRequest;
import com.example.admin.repository.ChairmanRepository;
import com.example.admin.service.ChairmanService;
import com.example.admin.util.UploadFileUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    private void saveChairman(Chairman chairman){
        chairmanRepository.save(chairman);
    }
}
