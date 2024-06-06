package com.example.user.serviceImpl;

import com.example.user.entity.User;
import com.example.user.entity.UserPasswordResetToken;
import com.example.user.model.authentication.EmailRequest;
import com.example.user.repository.UserPasswordResetTokenRepository;
import com.example.user.repository.UserRepository;
import com.example.user.service.PasswordResetTokenService;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {
    private final UserPasswordResetTokenRepository passwordResetTokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = LogManager.getLogger(PasswordResetTokenServiceImpl.class);

    public PasswordResetTokenServiceImpl(UserPasswordResetTokenRepository passwordResetTokenRepository,
                                         UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String createOrUpdatePasswordResetToken(EmailRequest emailRequest) {
        logger.info("createOrUpdatePasswordResetToken - Creating or updating password reset token by email "+emailRequest.email());
        User user = userRepository.findByEmailAndDeletedIsFalse(emailRequest.email())
                .orElseThrow(()-> new EntityNotFoundException("Chairman was not found by email "+emailRequest.email()));
        String token;
        if(userHasToken(user)){
            token = updateToken(user);
            logger.info("createOrUpdatePasswordResetToken - Password reset token has been updated");
        } else {
            token = createToken(user);
            logger.info("createOrUpdatePasswordResetToken - Password reset token has been created");
        }
        return token;
    }

    private String createToken(User user) {
        String token = UUID.randomUUID().toString();
        UserPasswordResetToken userPasswordResetToken =
                new UserPasswordResetToken(token, user);
        passwordResetTokenRepository.save(userPasswordResetToken);
        return token;
    }

    private String updateToken(User user) {
        String token = UUID.randomUUID().toString();
        user.getUserPasswordResetToken().setToken(token);
        user.getUserPasswordResetToken().setExpirationDate();
        user.getUserPasswordResetToken().setUsed(false);
        userRepository.save(user);
        return token;
    }

    private boolean userHasToken(User user) {
        return user.getUserPasswordResetToken() != null;
    }

    @Override
    public boolean isPasswordResetTokenValid(String token) {
        logger.info("isPasswordResetTokenValid - Checking if password reset token "+token+" valid");
        Optional<UserPasswordResetToken> userPasswordResetToken = passwordResetTokenRepository.findByToken(token);
        boolean isValid = userPasswordResetToken.isPresent()
                && !userPasswordResetToken.get().isUsed()
                && !userPasswordResetToken.get().getExpirationDate()
                .isBefore(Instant.now());
        logger.info("isPasswordResetTokenValid - Password reset token has been checked");
        return isValid;
    }

    @Override
    public void updatePassword(String token, String password) {
        logger.info("updatePassword() - Updating password by password reset token "+token+" password: "+password);
        UserPasswordResetToken userPasswordResetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(()-> new EntityNotFoundException("Password reset token was not found by token "+token));
        userPasswordResetToken.setUsed(true);
        User user = userPasswordResetToken.getUser();
        user.setPassword(passwordEncoder.encode(password));
        passwordResetTokenRepository.save(userPasswordResetToken);
        logger.info("updatePassword() - Password has been updated");
    }
}
