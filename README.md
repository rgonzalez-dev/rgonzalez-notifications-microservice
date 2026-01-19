# Notifications Microservice

Portfolio Component - Notifications API using Spring Boot 3.2.1 and Java 17

## Overview

This is a RESTful notifications microservice built with Spring Boot. It provides a simple API to manage and send notifications through various channels.

## Technologies

- **Java 17**
- **Spring Boot 3.2.1**
- **Maven 3.9.12**
- **Spring Boot Starter Web** - For REST API endpoints
- **Spring Boot Starter Actuator** - For health checks and monitoring
- **Spring Boot Starter Validation** - For input validation
- **Lombok** - To reduce boilerplate code
- **JUnit 5** - For unit testing

## Development Tool

This project is configured for **VS Code** with recommended extensions:
- Java Extension Pack
- Spring Boot Extension Pack
- Lombok Annotations Support

## Project Structure

```
src/
├── main/
│   ├── java/com/rgonzalez/notifications/
│   │   ├── NotificationsMicroserviceApplication.java  # Main application class
│   │   ├── controller/
│   │   │   └── NotificationController.java            # REST API endpoints
│   │   ├── service/
│   │   │   └── NotificationService.java              # Business logic
│   │   ├── model/
│   │   │   ├── Notification.java                     # Domain model
│   │   │   ├── NotificationType.java                 # Enum for notification types
│   │   │   └── NotificationStatus.java               # Enum for notification status
│   │   └── dto/
│   │       ├── NotificationRequest.java              # Request DTO
│   │       └── NotificationResponse.java             # Response DTO
│   └── resources/
│       └── application.properties                     # Application configuration
└── test/
    └── java/com/rgonzalez/notifications/
        ├── NotificationsMicroserviceApplicationTests.java  # Integration tests
        └── controller/
            └── NotificationControllerTest.java             # Controller tests
```

## API Endpoints

### Send Notification
- **POST** `/api/notifications`
- **Request Body:**
```json
{
  "recipient": "user@example.com",
  "subject": "Notification Subject",
  "message": "Notification message content",
  "type": "EMAIL"
}
```
- **Response:** `201 Created` with notification details

### Get All Notifications
- **GET** `/api/notifications`
- **Response:** `200 OK` with list of all notifications

### Get Notification by ID
- **GET** `/api/notifications/{id}`
- **Response:** `200 OK` with notification details or `404 Not Found`

### Actuator Endpoints
- **GET** `/actuator/health` - Application health information
- **GET** `/actuator/info` - Application information
- **GET** `/actuator/metrics` - Application metrics

## Notification Types

- `EMAIL` - Email notifications
- `SMS` - SMS notifications
- `PUSH` - Push notifications
- `IN_APP` - In-app notifications

## Notification Status

- `PENDING` - Notification queued for sending
- `SENT` - Notification successfully sent
- `FAILED` - Notification failed to send
- `DELIVERED` - Notification delivered to recipient

## Building the Project

```bash
mvn clean install
```

## Running the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## Running Tests

```bash
mvn test
```

## VS Code Configuration

The project includes VS Code configuration files:

- `.vscode/settings.json` - Java and Spring Boot settings
- `.vscode/launch.json` - Debug configurations
- `.vscode/extensions.json` - Recommended extensions

## Future Enhancements

- Integration with actual email/SMS providers
- Database persistence for notifications
- Message queuing (e.g., RabbitMQ, Kafka)
- User authentication and authorization
- Notification templates
- Retry mechanisms for failed notifications
- Notification history and analytics
