package com.example.admin.service;

import com.example.admin.model.admin.MFATokenResponse;
import dev.samstevens.totp.exceptions.QrGenerationException;

public interface MFATokenService {
    MFATokenResponse getMFATokenResponse();
    boolean verifyTotp(final String code, final String secret);
}
