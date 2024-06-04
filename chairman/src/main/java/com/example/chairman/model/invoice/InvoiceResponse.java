package com.example.chairman.model.invoice;

public record InvoiceResponse(
        String number,
        String file,
        UserNameResponse userNameResponse,
        String personalAccount
) {
}
