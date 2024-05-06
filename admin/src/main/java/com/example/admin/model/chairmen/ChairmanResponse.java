package com.example.admin.model.chairmen;

import com.example.admin.entity.ChairmanStatus;
import org.springframework.web.multipart.MultipartFile;

public record ChairmanResponse(
        String firstName,
        String lastName,
        String middleName,
        ChairmanStatus status,
        String email,
        String phoneNumber,
        String username,
        String avatar
) {
}
