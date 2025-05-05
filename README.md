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
- Header-based tenant resolution using X-Tenant-ID
- Sample Project entity per tenant
- Clean, extensible architecture

---

## 🛠️ Setup Instructions and Postman Test

### 1. 🐬 MySQL Configuration

Create a database named master_db:
sql
CREATE DATABASE master_db;

🧪 How to Test the API Using Postman
Follow the steps below to test the multi-tenant API using Postman.

📝 Step 1: Register a New Tenant
Method: POST

URL: /api/signup

Body (JSON):

json
Copy
Edit
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
✅ This will create a new tenant and automatically generate a dedicated database named after the webAddress (e.g., acmecorp).

📌 Step 2: Add a Project for the Tenant
Method: POST

URL: /api/projects

Headers:

X-Tenant-ID: acmecorp

Body (JSON):

json
Copy
Edit
{
  "name": "My First Project",
  "description": "Internal dashboard"
}
✅ Adds a project entry into the tenant’s isolated database.

📂 Step 3: Retrieve All Projects
Method: GET

URL: /api/projects

Headers:

X-Tenant-ID: acmecorp

✅ Fetches all projects related to the specified tenant.

🗑️ Step 4: Delete a Tenant
You can choose between soft or hard delete:

🔸 Soft Delete (mark as deleted, keeps DB):
Method: DELETE

URL: /api/signup/{id}/soft

🔹 Hard Delete (removes tenant and drops DB):
Method: DELETE

URL: /api/signup/{id}/hard

✅ Replace {id} with the tenant's signup ID.
