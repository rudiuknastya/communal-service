package com.example.chairman.model.invoice;

import java.time.LocalDate;

public record FilterRequest(
        int page,
        int pageSize,
        String number,
        String personalAccount,
        LocalDate creationDateFrom,
        LocalDate creationDateTo
) {
}
