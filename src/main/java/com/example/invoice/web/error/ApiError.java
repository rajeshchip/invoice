package com.example.invoice.web.error;

import java.time.Instant;

public record ApiError(String message, Instant timestamp) {
    public static ApiError of(String msg) { return new ApiError(msg, Instant.now()); }
}
