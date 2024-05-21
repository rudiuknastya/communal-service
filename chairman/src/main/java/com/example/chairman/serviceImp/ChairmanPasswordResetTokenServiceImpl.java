package com.example.chairman.serviceImp;


import com.example.chairman.entity.Chairman;
import com.example.chairman.entity.ChairmanPasswordResetToken;
import com.example.chairman.model.authentication.EmailRequest;
import com.example.chairman.repository.ChairmanPasswordResetTokenRepository;
import com.example.chairman.repository.ChairmanRepository;
import com.example.chairman.service.ChairmanPasswordResetTokenService;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChairmanPasswordResetTokenServiceImpl implements ChairmanPasswordResetTokenService {
    private final ChairmanPasswordResetTokenRepository chairmanPasswordResetTokenRepository;
    private final ChairmanRepository chairmanRepository;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = LogManager.getLogger(ChairmanPasswordResetTokenServiceImpl.class);

    public ChairmanPasswordResetTokenServiceImpl(ChairmanPasswordResetTokenRepository chairmanPasswordResetTokenRepository,
                                                 ChairmanRepository chairmanRepository,
                                                 PasswordEncoder passwordEncoder) {
        this.chairmanPasswordResetTokenRepository = chairmanPasswordResetTokenRepository;
        this.chairmanRepository = chairmanRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String createOrUpdatePasswordResetToken(EmailRequest emailRequest) {
        logger.info("createOrUpdatePasswordResetToken - Creating or updating password reset token by email "+emailRequest.email());
        Chairman chairman = chairmanRepository.findByEmailAndDeletedIsFalse(emailRequest.email())
                .orElseThrow(()-> new EntityNotFoundException("Chairman was not found by email "+emailRequest.email()));
        String token;
        if(chairmanHasToken(chairman)){
            token = updateToken(chairman);
            logger.info("createOrUpdatePasswordResetToken - Password reset token has been updated");
        } else {
            token = createToken(chairman);
            logger.info("createOrUpdatePasswordResetToken - Password reset token has been created");
        }
        return token;
    }

    private String createToken(Chairman chairman) {
        String token = UUID.randomUUID().toString();
        ChairmanPasswordResetToken chairmanPasswordResetToken =
                new ChairmanPasswordResetToken(token, chairman);
        chairmanPasswordResetTokenRepository.save(chairmanPasswordResetToken);
        return token;
    }

    private String updateToken(Chairman chairman) {
        String token = UUID.randomUUID().toString();
        chairman.getChairmanPasswordResetToken().setToken(token);
        chairman.getChairmanPasswordResetToken().setExpirationDate();
        chairman.getChairmanPasswordResetToken().setUsed(false);
        chairmanRepository.save(chairman);
        return token;
    }

    private boolean chairmanHasToken(Chairman chairman) {
        return chairman.getChairmanPasswordResetToken() != null;
    }

    @Override
    public boolean isPasswordResetTokenValid(String token) {
        logger.info("isPasswordResetTokenValid - Checking if password reset token "+token+" valid");
        Optional<ChairmanPasswordResetToken> chairmanPasswordResetToken = chairmanPasswordResetTokenRepository.findByToken(token);
        boolean isValid = chairmanPasswordResetToken.isPresent()
                && !chairmanPasswordResetToken.get().isUsed()
                && !chairmanPasswordResetToken.get().getExpirationDate()
                .isBefore(Instant.now());
        logger.info("isPasswordResetTokenValid - Password reset token has been checked");
        return isValid;
    }

    @Override
    public void updatePassword(String token, String password) {
        logger.info("updatePassword() - Updating password by password reset token "+token+" password: "+password);
        ChairmanPasswordResetToken chairmanPasswordResetToken = chairmanPasswordResetTokenRepository.findByToken(token)
                .orElseThrow(()-> new EntityNotFoundException("Password reset token was not found by token "+token));
        chairmanPasswordResetToken.setUsed(true);
        Chairman chairman = chairmanPasswordResetToken.getChairman();
        chairman.setPassword(passwordEncoder.encode(password));
        chairmanPasswordResetTokenRepository.save(chairmanPasswordResetToken);
        logger.info("updatePassword() - Password has been updated");
    }
}

