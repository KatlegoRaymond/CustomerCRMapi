# CustomerCRM API

A RESTful backend API for managing customers and addresses, built with Spring Boot, Spring Data JPA, and secured with Spring Security.

---

## Features

- Create, update, delete and fetch customers
- Create, update, delete and fetch addresses
- Filter by email, first name, last name, city, and postal code
- DTO layer for data transfer and validation
- Exception handling via `@RestControllerAdvice`
- Input validation using `jakarta.validation`
- Unit tests with JUnit 5 and Mockito
- CI/CD with GitHub Actions

---

## Tech Stack

- Java 17
- Spring Boot 3+
- Maven
- MySQL
- JUnit 5 + Mockito
- Postman (for API testing)
- GitHub Actions (CI/CD)
- REST API

---

## Project Structure

CustomerCRMapi/
│
├── src/
│   ├── main/
│   │   ├── java/com/bitroot/CustomerCRMapi/
│   │   │   ├── controller/
│   │   │   │   ├── CustomerController.java
│   │   │   │   └── AddressController.java
│   │   │   ├── dto/
│   │   │   │   ├── CustomerDto.java
│   │   │   │   └── AddressDto.java
│   │   │   ├── exception/
│   │   │   │   ├── GlobalExceptionHandler.java
│   │   │   │   └── ResourceNotFoundException.java
│   │   │   ├── model/
│   │   │   │   ├── Customer.java
│   │   │   │   └── Address.java
│   │   │   ├── repository/
│   │   │   │   ├── CustomerRepository.java
│   │   │   │   └── AddressRepository.java
│   │   │   ├── service/
│   │   │   │   ├── CustomerService.java
│   │   │   │   └── AddressService.java
│   │   │   └── service/impl/
│   │   │       ├── CustomerServiceImpl.java
│   │   │       └── AddressServiceImpl.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/bitroot/CustomerCRMapi/
│           ├── CustomerCrMapiApplicationTests.java
│           ├── controller/
│           │   ├── CustomerControllerTest.java
│           │   └── AddressControllerTest.java
│           ├── exception/
│           │   └── GlobalExceptionHandlerTest.java
│           └── service/impl/
│               ├── CustomerServiceImplTest.java
│               └── AddressServiceImplTest.java
│
├── .github/workflows/
│   └── ci-cd.yml
├── Dockerfile
├── k8s/
│   ├── deployment.yaml
│   └── service.yaml
├── .gitignore
├── pom.xml
└── README.md


---

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- MySQL database (or H2 for development)
- Git

### Setup

1. Clone the repository

   ``` cmd
   git clone https://github.com/yourusername/customercrm-api.git
   cd customercrm-api
   ```

2. Configure database connection in src/main/resources/application.properties
   
   spring.datasource.url=jdbc:mysql://localhost:3306/customercrm_db
   spring.datasource.username=root
   spring.datasource.password=Password
   
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect


3. Build and run the application

   ``` cmd
   mvn clean install
   mvn spring-boot:run
   ```
   App will start on: http://localhost:8080

4. Access API endpoints:

   -cmd
   http://localhost:8080/api/customers
   http://localhost:8080/api/addresses
   
---

### API Endpoints
   
	1. Customer
	
	| Method | URL                        | Description                 |
	| ------ | -------------------------- | --------------------------- |
	| POST   | `/api/customers`           | Create a new customer       |
	| GET    | `/api/customers`           | List all customers          |
	| GET    | `/api/customers/{id}`      | Get customer by ID          |
	| PUT    | `/api/customers/{id}`      | Update customer             |
	| DELETE | `/api/customers/{id}`      | Delete customer             |
	| GET    | `/api/customers/email`     | Get customer by email       |
	| GET    | `/api/customers/firstname` | Get customers by first name |
	| GET    | `/api/customers/lastname`  | Get customers by last name  |
	
	
	2. Customer
	
	| Method | URL                         | Description                     |
	| ------ | --------------------------- | ------------------------------- |
	| POST   | `/api/addresses`            | Create a new address            |
	| GET    | `/api/addresses`            | List all addresses              |
	| GET    | `/api/addresses/{id}`       | Get address by ID               |
	| PUT    | `/api/addresses/{id}`       | Update address                  |
	| DELETE | `/api/addresses/{id}`       | Delete address                  |
	| GET    | `/api/addresses/city`       | Search addresses by city        |
	| GET    | `/api/addresses/postalcode` | Search addresses by postal code |
	
---

### Testing

   ``` cmd
   mvn test
   ```

---

### Future Improvements

   - Switch to JWT for stateless authentication
   - Persist users and roles in the database
   - Add Swagger/OpenAPI documentation
   - Implement paging and sorting on list endpoints

---

### Author
   
   Created by Katlego Raymond Mpete
   Email: katlegoraymond.mpete@gmail.com
   LinkedIn: https://www.linkedin.com/in/katlego-raymond-mpete-0159631b7/

