package com.example.user.model.invoice;

import java.time.LocalDate;

public record TableInvoiceResponse(
        Long id,
        String number,
        String creationDate
) {
}
