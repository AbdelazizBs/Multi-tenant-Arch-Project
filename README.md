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
