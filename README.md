# Order Management System - REST API

A Spring Boot REST API for managing e-commerce orders with JWT authentication. Built as part of the Hotwax assignment.

## Features

- Customer signup and login with JWT authentication
- Complete CRUD operations for orders
- Order item management
- Input validation on all endpoints
- Proper exception handling
- BCrypt password encryption

## Tech Stack

- Java 21
- Spring Boot 4.0.1
- Spring Data JPA
- Spring Security with JWT
- Spring Validation
- MySQL Database
- Lombok
- Maven

## Database Setup

Run the SQL scripts in order:

```bash
mysql -u root -p < database/schema.sql
mysql -u root -p < database/data.sql
```

Update `src/main/resources/application.properties`:
```properties
spring.datasource.username=your_username
spring.datasource.password=your_password
```

## Running the Application

```bash
./mvnw clean install
./mvnw spring-boot:run
```

Application runs on `http://localhost:8080`

## API Endpoints

### Authentication
- `POST /auth/signup` - Register new customer
- `POST /auth/login` - Login and get JWT token

### Orders
- `POST /orders` - Create new order
- `GET /orders/{id}` - Get order details
- `PUT /orders/{id}` - Update order
- `DELETE /orders/{id}` - Delete order

### Order Items
- `POST /orders/{id}/items` - Add item to order
- `PUT /orders/{id}/items/{itemId}` - Update item
- `DELETE /orders/{id}/items/{itemId}` - Remove item

## Sample Requests

### Signup
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@example.com",
  "password": "password123"
}
```

### Login
```json
{
  "email": "john@example.com",
  "password": "password123"
}
```

### Create Order
```json
{
  "customerId": 1,
  "shippingContactMechId": 1,
  "billingContactMechId": 2,
  "orderDate": "2026-01-15",
  "orderItems": [
    {
      "productId": 1,
      "quantity": 2,
      "status": "pending"
    }
  ]
}
```

## Sample Data

After running `data.sql`:
- 2 customers (John Doe, Jane Smith)
- 5 products (T-Shirt, Jeans, Sneakers, Jacket, Hat)
- 3 addresses

## Error Handling

All endpoints return proper error responses:

```json
{
  "timestamp": "2026-01-15T10:30:00",
  "message": "Error description",
  "status": 400,
  "errors": {
    "field": "validation error"
  }
}
```

## Testing

Import `postman/Hotwax_Order_API.postman_collection.json` into Postman.

See `TESTING.md` for test scenarios and `API_DOCS.md` for complete API reference.

## Project Structure

```
src/main/java/
├── controller/     # REST endpoints
├── service/        # Business logic
├── repository/     # Data access
├── entity/         # JPA entities
├── dto/            # Request/Response objects
├── exception/      # Custom exceptions
└── util/           # Security config
```

