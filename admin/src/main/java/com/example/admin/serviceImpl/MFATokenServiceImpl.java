package com.example.admin.serviceImpl;

import com.example.admin.model.admin.MFATokenResponse;
import com.example.admin.service.MFATokenService;
import dev.samstevens.totp.code.CodeVerifier;
import dev.samstevens.totp.code.DefaultCodeGenerator;
import dev.samstevens.totp.code.DefaultCodeVerifier;
import dev.samstevens.totp.code.HashingAlgorithm;
import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.QrGenerator;
import dev.samstevens.totp.secret.SecretGenerator;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.samstevens.totp.util.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class MFATokenServiceImpl implements MFATokenService {
    private final SecretGenerator secretGenerator;
    private final QrGenerator qrGenerator;
    private final CodeVerifier codeVerifier;
    private final Logger logger = LogManager.getLogger(MFATokenServiceImpl.class);

    public MFATokenServiceImpl(SecretGenerator secretGenerator, QrGenerator qrGenerator,
                               CodeVerifier codeVerifier) {
        this.secretGenerator = secretGenerator;
        this.qrGenerator = qrGenerator;
        this.codeVerifier = codeVerifier;
    }

    @Override
    public MFATokenResponse getMFATokenResponse() {
        logger.info("getMFATokenResponse() - Getting MFATokenResponse");
        String key = generateSecretKey();
        String qrCode = "";
        try {
            qrCode = getQRCode(key);
        } catch (QrGenerationException e) {
            logger.error(e.getMessage());
        }
        MFATokenResponse mfaTokenResponse = new MFATokenResponse(qrCode, key);
        logger.info("getMFATokenResponse() - MFATokenResponse has been got");
        return mfaTokenResponse;
    }

    private String generateSecretKey() {
        String code = secretGenerator.generate();
        return code;
    }
    private String getQRCode(String secret) throws QrGenerationException {
        QrData data = new QrData.Builder().label("MFA")
                .secret(secret)
                .issuer("Communal service")
                .algorithm(HashingAlgorithm.SHA256)
                .digits(6)
                .period(30)
                .build();
        return Utils.getDataUriForImage(
                qrGenerator.generate(data),
                qrGenerator.getImageMimeType());
    }

    @Override
    public boolean verifyTotp(String code, String secret) {
        return codeVerifier.isValidCode(secret, code);
    }
}
