package com.example.invoice.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InvoiceDomainTest {

    @Test
    void testCreateInvoice() {

        Invoice invoice = new Invoice(BigDecimal.valueOf(100), LocalDate.now().plusDays(5));

        assertThat(invoice.getAmount()).isEqualTo(BigDecimal.valueOf(100).setScale(2));
        assertThat(invoice.getPaidAmount()).isEqualTo(BigDecimal.ZERO.setScale(2));
        assertThat(invoice.getStatus()).isEqualTo(InvoiceStatus.PENDING);
    }

    @Test
    void testApplyPaymentFull() {
        Invoice invoice = new Invoice(BigDecimal.valueOf(100), LocalDate.now());
        invoice.pay(BigDecimal.valueOf(100));

        assertThat(invoice.getPaidAmount()).isEqualTo(BigDecimal.valueOf(100).setScale(2));
        assertThat(invoice.getStatus()).isEqualTo(InvoiceStatus.PAID);
    }

    @Test
    void testApplyPaymentPartial() {
        Invoice invoice = new Invoice(BigDecimal.valueOf(100), LocalDate.now());
        invoice.pay(BigDecimal.valueOf(40));

        assertThat(invoice.getPaidAmount()).isEqualTo(BigDecimal.valueOf(40).setScale(2));
        assertThat(invoice.getStatus()).isEqualTo(InvoiceStatus.PENDING);
    }

    @Test
    void testApplyPaymentOnPaidInvoiceThrows() {
        Invoice invoice = new Invoice(BigDecimal.valueOf(50), LocalDate.now());
        invoice.pay(BigDecimal.valueOf(50));
        assertThrows(IllegalStateException.class, () -> invoice.pay(BigDecimal.valueOf(10)));
    }

    @Test
    void testIsOverdue() {
        Invoice invoice = new Invoice(BigDecimal.valueOf(100), LocalDate.now().minusDays(1));
        assertThat(invoice.isOverdue(LocalDate.now())).isTrue();

    }
}
