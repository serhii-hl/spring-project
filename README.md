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

## Postman collection:

https://web.postman.co/workspace/My-Workspace~1aaecfe4-bf1e-4ea4-82e2-d2c99d18913d/collection/44947335-a272c8b3-efc8-4bff-8cf0-d904e98af5e7?action=share&creator=44947335

## Swagger link:

http://localhost:8080/swagger-ui/index.html

## Setup

# First, ensure you have the following installed:`
- Java Development Kit (JDK)
- Docker and Docker Compose
# Clone the repository (https://github.com/serhii-hl/spring-project)
# Create an .env file with the necessary environment variables. (See .env.template for a sample.)
# Repackage the project with mvn clean package command
# Run the following commands to build and start the Docker containers:
- docker-compose build 
- docker-compose up.
# The application should now be running at http://localhost:8080
