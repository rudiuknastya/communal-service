package com.example.user.model.messages;

import java.time.LocalDate;

public record FilterRequest(
        int page,
        int pageSize,
        String subject,
        LocalDate dateFrom,
        LocalDate dateTo
) {
}
