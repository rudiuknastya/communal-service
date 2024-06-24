package com.example.user.model.invoice;

import java.time.LocalDate;

public record FilterRequest(
        int page,
        int pageSize,
        String number,
        LocalDate creationDateFrom,
        LocalDate creationDateTo
) {
}
