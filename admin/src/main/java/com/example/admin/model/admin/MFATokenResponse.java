package com.example.admin.model.admin;

public record MFATokenResponse(
        String qrCode,
        String qrCodeKey
) {
}
