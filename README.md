Pizza Ordering Management System

A microservices-based pizza ordering backend system built using **Spring Boot**, with secure Google OAuth login, payment integration, and notification features.

---

## Features

- Place, view, and manage pizza orders
- Customer management and profile handling
- Online and Cash-on-Delivery payment modes
- Google OAuth 2.0 login (JWT-secured)
- Notifications via RabbitMQ (Email/SMS)
- Resilience4j Circuit Breaker for fault tolerance
- Service discovery with Eureka
- API Gateway using Spring Cloud Gateway
- Centralized configuration using Spring Cloud Config Server

---

## Microservices Used

| Service              | Description                              |
|----------------------|------------------------------------------|
| `OrderDetails`       | Manages pizza orders                     |
| `PizzaDetails`       | Lists available pizzas and prices        |
| `PaymentDetails`     | Initiate payment
| 'PayPalIntegration   | Handles payment processing               |
| `CustomerDetails`    | Manages customer profiles                |
| `Notification`       | Sends notifications via RabbitMQ         |
| `OAuthWithJwt`       | Google OAuth 2.0 Authentication          |
| `SpringCloudGateway` | Gateway for routing and authentication   |
| `SpringConfigServer` | Centralized configuration management     |
| `EurekaServer`       | Service registry and discovery           |

---

## Tech Stack

- **Backend:** Java 17, Spring Boot
- **Security:** OAuth2, JWT
- **Messaging:** RabbitMQ
- **Database:** PostgreSQL
- **DevOps:** Git
- **API Gateway:** Spring Cloud Gateway
- **Service Discovery:** Eureka
- **Fault Tolerance:** Resilience4j
- **Config Management:** Spring Cloud Config

---

## How to Run the Project Locally

1. **Clone the repository**
   ```bash
   git clone https://github.com/Akshayaraghu310/pizza-ordering-management-system.git
   cd pizza-ordering-management-system

2. Start Required Services

Start Eureka Server and Spring Cloud Config Server
Start springCloud Gateway
Make sure PostgreSQL is running and databases are created
Gateway URL: http://localhost:8080
Login via Google OAuth to access protected APIs
Set up your own credentials and database details in each service's application.properties
