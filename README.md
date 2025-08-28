# 📘 Invoice Management System (Spring Boot)

## 📌 Overview

This project implements a simple Invoice Management System using Spring Boot.

It provides APIs to:

  	 Create invoices

  	 Retrieve invoices

  	 Apply payments

  	 Process overdue invoices with late fees

This interim delivery includes:

✅ Full CRUD flows (Invoice, Payment, Overdue Processing)

✅ Persistence using H2 (file-based DB)

✅ Interactive API documentation via Swagger UI

✅ Clean modular design (Domain, Repository, Service, Controller layers)


🚀 Final submission will additionally include JUnit test coverage and Docker Compose setup.


## ⚙️ Tech Stack

Java 17

Spring Boot 3.x

Spring Data JPA

H2 Database (file-based persistence)

Swagger / OpenAPI 3

Maven


🗂️ Project Structure
src/main/java/com/example/invoice/

├── InvoiceServiceApplication.java   # Spring Boot entry point

├── domain/                          # Entities & Enums

│   ├── Invoice.java

│   └── InvoiceStatus.java

├── repo/                            # JPA Repositories

│   └── InvoiceRepository.java

├── service/                         # Business Logic Layer

│   └── InvoiceService.java

└── web/                             # Controllers + DTOs

src/main/resources/

└── application.yaml                 # DB + Swagger config

src/test/java/com/example/invoice/

└── domain/InvoiceDomainTest.java    # Initial unit tests

pom.xml

README.md


## 🔗 API Endpoints

  Base URL: http://localhost:8080

### 1️⃣ Create Invoice

**Request**

POST /invoices

Content-Type: application/json

{

  "amount": 199.99,
  
  "due_date": "2025-09-11"

}


**Response**

{
  "id": "1234"
}

### 2️⃣ Get All Invoices

**Request**

GET /invoices


**Response**

[

  {
  
    "id": "1234",
    
    "amount": 199.99,
    
    "paid_amount": 0,
    
    "due_date": "2025-09-11",
    
    "status": "PENDING"
  
  }

]


### 3️⃣ Apply Payment

**Request**


POST /invoices/{id}/payments

Content-Type: application/json

{

  "amount": 159.99
  
}


**Response**


{

  "id": "1234",
  
  "amount": 199.99,
  
  "paid_amount": 159.99,
  
  "due_date": "2025-09-11",
  
  "status": "PARTIALLY_PAID"
  
}

### 4️⃣ Process Overdue Invoices

**Request**


POST /invoices/process-overdue

Content-Type: application/json

{

  "late_fee": 10.5,
  
  "overdue_days": 10
  
}



**Response**

[

  {
  
    "id": "1234",
  
    "amount": 199.99,
    
    "paid_amount": 0,
    
    "due_date": "2025-08-01",
    
    "status": "OVERDUE",
    
    "late_fee_applied": 10.5
  
  }

]

## 📖 Swagger API Docs


**Swagger UI:** 👉 http://localhost:8080/swagger-ui.html

**OpenAPI spec (JSON):** 👉 http://localhost:8080/v3/api-docs


## 🗄️ Database (H2 - File Based)

**Console:** http://localhost:8080/h2-console

**JDBC URL:** jdbc:h2:file:./data/invoice-db

**Username:** sa

**Password:** (blank)

**File location:** ./data/invoice-db.mv.db (auto-created)

## 🔜 Next Steps (Final Delivery)


Add JUnit test coverage for Service & Controller layers

Add Docker Compose setup (API + DB orchestration)
