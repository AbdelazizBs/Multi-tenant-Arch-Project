# 🏗️ Multi-Tenant Spring Boot Application

This project implements a dynamic multi-tenant architecture using Spring Boot and MySQL. Each tenant (user/company) gets a dedicated database created at signup time.

---

## 🔧 Technologies

- Spring Boot
- Spring Data JPA
- Hibernate Multi-Tenancy
- MySQL
- RESTful API

---

## 📦 Features

- Dynamic database creation per tenant
- Soft and hard delete for tenants
- Header-based tenant resolution using `X-Tenant-ID`
- Sample `Project` entity per tenant
- Clean, extensible architecture

---

## 🛠️ Setup Instructions

### 1. 🐬 MySQL Configuration

Create a database named `master_db`:
```sql
CREATE DATABASE master_db;
🧪 How to Test (Postman)
Step 1: Register New Tenant
POST /api/signup
Body:
{
  "firstName": "John",
  "lastName": "Doe",
  "companyName": "Acme Corp",
  "country": "US",
  "email": "john@acme.com",
  "password": "123456",
  "webAddress": "acmecorp",
  "fullUrl": "acmecorp.test.com",
  "phone": "123456789",
  "users": 10,
  "termsAccepted": true,
  "newsletterSubscribed": false
}
