## Spring-project: Book Store API

A Spring Boot-based RESTful web service for managing an online book store.  
Admins can manage books, while customers can register, browse the catalog, 
and add items to a shopping cart to prepare for purchase.

## Project goals:

The goal of this project was to simulate a real-world e-commerce backend using modern Java technologies.  
This includes role-based authorization, secure registration/login, catalog management, 
shopping cart, and order processing logic.

## Tech stack:

- Java 17
- Spring Boot
- Spring Security (JWT)
- Spring Data JPA
- Hibernate
- MySQL
- Swagger/OpenAPI
- Docker
- Maven
- JUnit + Mockito

## Roles:

- Admin: Can add, update, delete books; view all users and orders.
- User: Can register/login, browse books, add to cart, and place orders.
- 
## Main Features:

# Authentication & Authorization
- Secure login & registration with JWT tokens
- Role-based access to endpoints

# Book Management
- Admin can create, update, delete books
- Book search & pagination

# Shopping Cart
- Customers can add/remove books to/from cart
- View current cart

# Order Management
- Place orders based on cart
- View past orders

## Entity Relationship diagram:

Here is the ERD representing all main entities and relationships in the bookstore system:

![ER Diagram](/book-store.drawio.png)

## Code Quality

This project uses the following tools to maintain clean and consistent code:

- Checkstyle – to enforce code style rules
- Maven Compiler Plugin – to ensure compatibility
- JUnit + Mockito – for unit and integration testing

## Video guide

https://www.loom.com/share/5a487b110ed04385b54fa2d73ab0aceb?sid=b4c45c83-60d3-460b-afd8-68319314a5af

## Setup

1. Download my project to your PC
2. Please have installed Docker
3. Before running the application, create a `.env` file in the root directory 
by copying the provided `.env.template`
4. Test all endpoints on Postman :)
