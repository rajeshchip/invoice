package com.example.invoice.web.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record PaymentRequest(@NotNull @DecimalMin("0.01") BigDecimal amount) {}
