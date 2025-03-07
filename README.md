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
- MySQL (for production use)
- Postman (for API testing)

### Setup

1. **Create a Spring Boot Project**:
   - Go to Spring Initializr
   - Select:
     - Project: Maven
     - Language: Java
     - Spring Boot Version: 2.7.x
   - Enter Project Metadata:
     - Group: `com.parking`
     - Artifact: `parking-system`
     - Name: `parking-system`
     - Package Name: `com.parking`
   - Add Dependencies:
     - Spring Web
     - Spring Data JPA
     - H2 Database (for in-memory testing)
     - MySQL Driver (for production)
   - Click Generate and extract the ZIP file.
   - Open the project in your IDE (VS Code or IntelliJ).

2. **Configure `application.properties`**:
   ```properties
   spring.datasource.url=jdbc:h2:mem:parkingdb
   spring.datasource.driver-class-name=org.h2.Driver
   spring.datasource.username=sa
   spring.datasource.password=
   spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
   spring.h2.console.enabled=true
   spring.h2.console.path=/h2-console
   spring.jpa.hibernate.ddl-auto=update
   parking.max-slots=100
   ```
   
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
