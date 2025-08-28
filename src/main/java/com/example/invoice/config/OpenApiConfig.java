/**
 * Author: Rajesh Sukendiramani
 * Date: 28th Aug 2025
 * Change Description: As part of Demonstration of Invoice Application
 */
package com.example.invoice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Invoice Service API",
        version = "1.0",
        description = "API documentation for Invoice Management System"
    )
)
public class OpenApiConfig {
}
