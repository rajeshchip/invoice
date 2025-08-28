package com.example.invoice.web;

import com.example.invoice.service.InvoiceService;
import com.example.invoice.web.dto.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/invoices")
@Tag(name = "Invoice", description = "Endpoints for managing invoices")
public class InvoiceController {
    private final InvoiceService service;

    public InvoiceController(InvoiceService service) { this.service = service; }

    @PostMapping
    @Operation(summary = "Create a new invoice")
    public ResponseEntity<Map<String, Integer>> create(@Valid @RequestBody CreateInvoiceRequest req) {
        Integer id = service.create(req.amount(), req.due_date());
        return ResponseEntity.created(URI.create("/invoices/" + id)).body(Map.of("id", id));
    }

    @GetMapping
    @Operation(summary = "List all invoices")
    public List<InvoiceResponse> list() {
    	System.out.println("Welcome");
        return service.findAll().stream().map(InvoiceResponse::from).collect(Collectors.toList());
    }
    
	/*
	 * @GetMapping("/{id}")
	 * 
	 * @Operation(summary = "Get invoice by ID", description =
	 * "Returns a single invoice") public Invoice getInvoiceById(@PathVariable Long
	 * id) { return service.find }
	 */

    
    @PostMapping("/{id}/payments")
    @Operation(summary = "Process the payment for invoice")
    public ResponseEntity<Void> pay(@PathVariable Integer id, @Valid @RequestBody PaymentRequest req) {
        service.pay(id, req.amount());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/process-overdue")
    @Operation(summary = "Process all overdue invoices")
    public Map<String, Object> processOverdue(@Valid @RequestBody ProcessOverdueRequest req) {
        List<Integer> created = service.processOverDue(req.late_fee(), req.overdue_days(), LocalDate.now());
        return Map.of(
                "created_invoices", created,
                "count", created.size()
        );
    }
}
