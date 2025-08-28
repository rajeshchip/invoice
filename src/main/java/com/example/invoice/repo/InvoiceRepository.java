/**
 * 
 */
package com.example.invoice.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.invoice.domain.Invoice;

/**
 * 
 */
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

}
