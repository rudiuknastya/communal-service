package com.example.chairman.model.registrationRequest;

public record RegistrationRequestResponse(
        String fullName,
        String apartmentNumber,
        String area,
        String personalAccount,
        String phoneNumber,
        String email,
        HouseResponse houseResponse
) {
}
