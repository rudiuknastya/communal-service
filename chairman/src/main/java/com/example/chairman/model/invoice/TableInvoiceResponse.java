package com.example.chairman.model.invoice;

import java.time.LocalDate;

public record TableInvoiceResponse(
        Long id,
        String number,
        String personalAccount,
        String creationDate
) {
}
