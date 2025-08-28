/**
 * Author: Rajesh Sukendiramani
 * Date: 28th Aug 2025
 * Change Description: As part of Demonstration of Invoice Application
 */

package com.example.invoice.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.invoice.domain.Invoice;
import com.example.invoice.repo.InvoiceRepository;


/**
 * This class contains business logic of all the APIs exposed
 * 1. Create Invoice
 * 2. List All Invoices
 * 3. Post Payment
 * 4. Process OverDue Invoices
 */

@Service
public class InvoiceService {
	
	private final InvoiceRepository repo;
	
	public InvoiceService(InvoiceRepository repo) {
		this.repo = repo;
	}
	
	public Integer create ( BigDecimal amount, LocalDate dueDate) {
		
		Invoice inv = new Invoice(amount, dueDate);
		repo.save(inv);
		return inv.getId();		
		
	}
	
	public List<Invoice> findAll(){
		return repo.findAll();
	}
	
	public void pay(Integer id, BigDecimal amount) {
		
		Invoice inv = repo.findById(id).orElseThrow(()-> new NoSuchElementException("Invoice Not found!"));
		inv.pay(amount);
		repo.save(inv);
		
	}
	
	@Transactional
	public List<Integer> processOverDue(BigDecimal lateFee, int overDue, LocalDate today){
		List<Integer> newInvList = new ArrayList<>();
		for (Invoice inv: repo.findAll()) {
			Invoice newInv = inv.processOverdue(lateFee, overDue, today);
			if (newInv != null) {
				repo.save(inv);
				repo.save(newInv);
				newInvList.add(newInv.getId());
			}
		}
		return newInvList;
	}	

}
