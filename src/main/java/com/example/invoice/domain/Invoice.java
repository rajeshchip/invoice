/**
 * Author: Rajesh Sukendiramani
 * Date: 28th Aug 2025
 * Change Description: As part of Demonstration of Invoice Application
 */
package com.example.invoice.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.*;

/** Domain Class to represent table 'INVOICE'
 *  Columns: 
 *  	ID : Auto Generated Number
 *  	INV_AMOUNT : Invoice Amount (It won't be changed after creation)
 *  	PAID_AMOUNT : Received payment amount for this invoice (Whenever payment is received, it'll be incremented with payment amount
 *  	DUE_DATE : Due Date of the Invoice
 *  	STATUS : Enumerated values of PENDING, PAID, VOID*  	
 * 
 */

@Entity
@Table(name = "invoice")
public class Invoice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false, precision = 12, scale = 2)
	private BigDecimal invAmount;

	@Column(nullable = false, precision = 12, scale = 2)
	private BigDecimal paidAmount;

	@Column(nullable = false)
	private LocalDate dueDate;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 10)
	private InvoiceStatus status;

	protected Invoice() {
	}

	public Invoice(BigDecimal invAmount, LocalDate dueDate) {
		
		this.invAmount = invAmount.setScale(2, RoundingMode.HALF_UP);
		this.dueDate = Objects.requireNonNull(dueDate);
		this.paidAmount = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
		this.status = InvoiceStatus.PENDING;
	}

	public Integer getId() {
		return id;
	}

	public BigDecimal getAmount() {
		return invAmount;
	}

	public BigDecimal getPaidAmount() {
		return paidAmount;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public InvoiceStatus getStatus() {
		return status;
	}

	public boolean isPending() {
		return status == InvoiceStatus.PENDING;
	}

	public boolean isOverdue(LocalDate today) {
		return isPending() && dueDate.isBefore(today);
	}

	public void pay(BigDecimal amountToPay) {
		if (!isPending())
			throw new IllegalStateException("Cannot pay invoice with status " + status);

		if (amountToPay == null || amountToPay.signum() <= 0)
			throw new IllegalArgumentException("payment must be > 0");
		
		BigDecimal newPaid = paidAmount.add(amountToPay);
		
		if (newPaid.compareTo(invAmount) > 0)
			throw new IllegalArgumentException("overpayment not allowed");
		
		paidAmount = newPaid.setScale(2, RoundingMode.HALF_UP);
		if (paidAmount.compareTo(invAmount) == 0) {
			status = InvoiceStatus.PAID;
		}
	}
	
	/**
     * Process this invoice if overdue as of `today`.
     * Returns a new invoice if one should be created due to overdue rules; otherwise returns null.
     */
    public Invoice processOverdue(BigDecimal lateFee, int overdueDays, LocalDate today) {
        if (!isOverdue(today)) return null;
        if (lateFee == null || lateFee.signum() < 0) throw new IllegalArgumentException("lateFee must be >= 0");
        if (overdueDays <= 0) throw new IllegalArgumentException("overdueDays must be > 0");

        BigDecimal remaining = invAmount.subtract(paidAmount);
        BigDecimal newAmount;

        if (paidAmount.signum() > 0 && remaining.signum() > 0) { // partially paid
            this.status = InvoiceStatus.PAID; // mark original as paid per spec
            newAmount = remaining.add(lateFee);
        } else if (paidAmount.signum() == 0) { // not paid at all
            this.status = InvoiceStatus.VOID;
            newAmount = invAmount.add(lateFee);
    } else { // fully paid, shouldn't be here because pending & overdue implies remaining > 0
            return null;
        }

        return new Invoice(newAmount.setScale(2, RoundingMode.HALF_UP), today.plusDays(overdueDays));
    }
}


