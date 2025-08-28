package com.example.invoice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InvoiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvoiceApplication.class, args);
	}
	
	// Swap this Bean to a JPA repository later without changing service/controller
/*    @Bean
    public InvoiceRepository invoiceRepository() {
        return new InMemoryInvoiceRepository();
    }*/


}
