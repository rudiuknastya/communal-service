package com.example.user.serviceImpl;

import com.example.user.entity.User;
import com.example.user.entity.enums.UserStatus;
import com.example.user.model.user.MyUserDetails;
import com.example.user.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final Logger logger = LogManager.getLogger(UserDetailsServiceImpl.class);
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("loadUserByUsername() - Finding user by username "+username+" for user details");
        User user = userRepository.findByUsernameAndDeletedIsFalse(username)
                .orElseThrow(() -> new UsernameNotFoundException("User was not found by username "+username));
        MyUserDetails myUserDetails = new MyUserDetails(user);
        if (user.getStatus().equals(UserStatus.DISABLED)){
            myUserDetails.setEnabled(false);
        }
        if(user.getStatus().equals(UserStatus.NEW)){
            myUserDetails.setAccountNonLocked(false);
        }
        logger.info("loadUserByUsername() - User was found");
        return myUserDetails;
    }
}
