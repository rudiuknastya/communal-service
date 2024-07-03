package com.example.chairman.model.registrationRequest;

import com.example.chairman.entity.enums.RequestStatus;

public record FilterRequest(
        int page,
        int pageSize,
        String fullName,
        String email,
        RequestStatus status
) {
}
