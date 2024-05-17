package com.example.chairman.serviceImp;

import com.example.chairman.entity.Chairman;
import com.example.chairman.entity.ChairmanStatus;
import com.example.chairman.model.chairman.ChairmanDetails;
import com.example.chairman.repository.ChairmanRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final ChairmanRepository chairmanRepository;
    private final Logger logger = LogManager.getLogger(UserDetailsServiceImpl.class);
    public UserDetailsServiceImpl(ChairmanRepository chairmanRepository) {
        this.chairmanRepository = chairmanRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("loadUserByUsername() - Finding chairman by username "+username+" for chairman details");
        Chairman chairman = chairmanRepository.findByUsernameAndDeletedIsFalse(username)
                .orElseThrow(() -> new UsernameNotFoundException("Chairman was not found by email "+username));
        ChairmanDetails chairmanDetails = new ChairmanDetails(chairman);
        if (chairman.getStatus().equals(ChairmanStatus.DISABLED)){
            chairmanDetails.setEnabled(false);
        }
        logger.info("loadUserByUsername() - Chairman was found");
        return chairmanDetails;
    }
}
