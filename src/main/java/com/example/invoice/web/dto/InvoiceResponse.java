package com.example.invoice.web.dto;

import com.example.invoice.domain.Invoice;
import com.example.invoice.domain.InvoiceStatus;
import java.math.BigDecimal;
import java.time.LocalDate;

public record InvoiceResponse(
        Integer id,
        BigDecimal amount,
        BigDecimal paid_amount,
        LocalDate due_date,
        InvoiceStatus status
) {
    public static InvoiceResponse from(Invoice i) {
        return new InvoiceResponse(i.getId(), i.getAmount(), i.getPaidAmount(), i.getDueDate(), i.getStatus());
    }
}
