# ToDo List API (Java Spring Boot) with Basic Authentication

This is a simple ToDo List API built using Java Spring Boot that supports basic authentication. It allows you to manage your tasks with basic CRUD operations (Create, Read, Update, Delete).

## Features

- User registration with email and password
- User login via Basic Authentation
- Manipulate tasks: View, Create, Update

## Prerequisites

Before you begin, ensure you have the following installed on your system:

- Java Development Kit (JDK) 17
- Maven (for building and running the project)
- PostgreSQL (or any other relational database)

## Authentication

This API uses Basic Authentication for securing the endpoints. When making requests, provide your username and password as Base64-encoded credentials in the `Authorization` header as follows:

```http
Authorization: Basic <Base64(username:password)>
```

## How to run

1. Clone the repository to your local machine.

2. Open the project in your IDE.

3. Configure the database settings in `src/main/resources/application.properties`:

   ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/todolist
    spring.datasource.username=postgres
    spring.datasource.password=postgres
    spring.jpa.hibernate.ddl-auto=create-drop
   ```

   Modify the URL, username, and password to match your database configuration.

4. Build and run the application:

   ```bash
   mvn spring-boot:run
   ```

The API will be accessible at `http://localhost:8080`.
