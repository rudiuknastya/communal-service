package com.example.chairman.model.message;

import java.time.LocalDate;

public record FilterRequest(
        int page,
        int pageSize,
        String fullName,
        Long apartmentNumber,
        String phoneNumber,
        String subject,
        LocalDate dateFrom,
        LocalDate dateTo
) {
}
