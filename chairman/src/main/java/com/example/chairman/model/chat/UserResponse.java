package com.example.chairman.model.chat;

public record UserResponse(
        Long id,
        String fullName,
        String avatar,
        String houseNumber,
        Long apartmentNumber
) {
}
