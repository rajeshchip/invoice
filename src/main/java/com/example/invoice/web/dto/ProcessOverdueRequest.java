package com.example.invoice.web.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProcessOverdueRequest(
        @NotNull @DecimalMin("0.00") BigDecimal late_fee,
        @Min(1) int overdue_days
) {}
