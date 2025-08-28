package com.example.invoice.web.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateInvoiceRequest(
	        @NotNull @DecimalMin(value = "0.01") BigDecimal amount,
	        @NotNull LocalDate due_date
) {}

