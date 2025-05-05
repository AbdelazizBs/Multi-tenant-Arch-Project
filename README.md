🏗️ Multi-Tenant Spring Boot Application
This project implements a dynamic multi-tenant architecture using Spring Boot and MySQL. Each tenant (user/company) gets a dedicated database created at signup time.

🔧 Technologies
Spring Boot

Spring Data JPA

Hibernate Multi-Tenancy

MySQL

RESTful API

📦 Features
Dynamic database creation per tenant

Soft and hard delete for tenants

Header-based tenant resolution using X-Tenant-ID

Sample Project entity per tenant

Clean, extensible architecture

🛠️ Setup Instructions
1. 🐬 MySQL Configuration
Create a database named master_db:
CREATE DATABASE master_db;
🧪 How to Test (Using Postman)
Step 1: Register a New Tenant
Method: POST

Endpoint: /api/signup

Body (JSON):
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
Description: This step registers a new tenant (company) in the system and creates a new database for that tenant.
Step 2: Add a Project for the Tenant
Method: POST

Endpoint: /api/projects

Headers:

X-Tenant-ID: acmecorp (Replace acmecorp with the web address of your tenant)

Body (JSON):
{
  "name": "My First Project",
  "description": "Internal dashboard"
}
Description: Adds a new project for the specified tenant.
Step 3: Retrieve All Projects for the Tenant
Method: GET

Endpoint: /api/projects

Headers:

X-Tenant-ID: acmecorp (Replace acmecorp with your tenant's web address)

Description: Fetches all projects for the given tenant.

Step 4: Delete a Tenant
You can delete a tenant either through a soft delete (removes tenant's data but keeps the database) or a hard delete (drops the tenant's database completely).

Soft Delete:
Method: DELETE

Endpoint: /api/signup/{id}/soft

Description: Soft deletes a tenant, marking their data as deleted but retaining the database.

Hard Delete:
Method: DELETE

Endpoint: /api/signup/{id}/hard

Description: Hard deletes a tenant, including dropping their database.

Important: Replace {id} with the tenant's unique ID.

📎 Notes
Ensure that the webAddress is unique for each tenant, as it is used to name the tenant's database.

Authentication (e.g., JWT) can be integrated later.

📁 Project Structure Highlights
SignupEntity → Master DB

Project → Tenant DB
