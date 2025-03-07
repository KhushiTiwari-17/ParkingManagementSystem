# Parking Management System

This project is a **Parking Management System** built using **Spring Boot**. It provides RESTful APIs to manage users, vehicles, and parking slots. The system supports user registration, vehicle registration, and parking slot management.

## Project Structure

1. **Entities**: Defines the data models for Users, Vehicles, and Parking Slots.
2. **Repositories**: Interfaces for database interactions.
3. **Services**: Contains business logic.
4. **Controllers**: Exposes RESTful APIs.
5. **Exception Handling**: Custom exceptions and global exception handler.

### Prerequisites

- Java 8 or higher
- Maven
- Postman (for API testing)
   
### Running the Application

1. **Build the Project**:
   ```bash
   mvn clean install
   ```

2. **Run the Application**:
   ```bash
   mvn spring-boot:run
   ```

3. **Access the H2 Console**:
   - URL: `http://localhost:8080/h2-console`
   - JDBC URL: `jdbc:h2:mem:parkingdb`
   - User Name: `sa`
   - Password: (leave blank)

### Using the Postman Collection

1. **Import the Postman Collection**:
   - Open Postman.
   - Click on `Import` in the top left corner.
   - Select the Postman collection file provided with this project.

2. **Run the API Requests**:
   - Use the imported collection to test the various endpoints.
   - Ensure the application is running locally before making requests.

### API Endpoints

- **User Management**:
  - Register User: `POST /users/register`
  - Get All Users: `GET /users/all`
  - Update User: `PUT /users/{id}`
  - Delete User: `DELETE /users/{id}`

- **Vehicle Management**:
  - Register Vehicle: `POST /vehicles/register`
  - Get All Vehicles: `GET /vehicles/all`
  - Update Vehicle: `PUT /vehicles/{id}`
  - Delete Vehicle: `DELETE /vehicles/{id}`

- **Parking Slot Management**:
  - Add Slot: `POST /parking/add-slot`
  - Update Slot: `PUT /parking/update-slot/{id}`
  - Get All Slots: `GET /parking/slots`
  - Delete Slot: `DELETE /parking/{id}`
