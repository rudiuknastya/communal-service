package com.example.chairman.model.registrationRequest;

import com.example.chairman.entity.enums.RequestStatus;

public record TableRegistrationRequestResponse(
        Long id,
        String fullName,
        String email,
        RequestStatus status
) {
}
