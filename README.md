# Library Management System

A Java-based library management system that implements the DAO (Data Access Object) pattern to manage books, members, and borrowing records. The system uses PostgreSQL for data persistence and follows clean code principles with a clear separation of concerns.

## Project Structure

```
assessment/src/main/java/com/week2assessment/
├── Main.java                    # Application entry point
├── LibraryManagementSystem.java # Business logic layer
├── LibraryUI.java              # User interface layer
├── LibraryManagement.java      # Entity definitions (Book, Member, BorrowingRecords)
├── BookDAO.java                # Book Data Access Interface
├── BookDAOImpl.java            # Book Data Access Implementation
├── MemberDAO.java              # Member Data Access Interface
├── MemberDAOImpl.java          # Member Data Access Implementation
├── BorrowingRecordsDAO.java    # Borrowing Records Data Access Interface
└── BorrowingRecordsDAOImpl.java # Borrowing Records Data Access Implementation
```

## Design Approach

The application follows several key design principles:

1. **DAO Pattern**
   - Separates data persistence logic from business logic
   - Each entity (Book, Member, BorrowingRecords) has its own DAO interface and implementation
   - Makes the system flexible for different storage implementations

2. **Separation of Concerns**
   - UI Layer (`LibraryUI.java`): Handles all user interactions
   - Business Layer (`LibraryManagementSystem.java`): Contains business logic and orchestrates operations
   - Data Access Layer (DAOs): Manages database operations
   - Entity Layer (`LibraryManagement.java`): Defines data models

3. **Clean Code Principles**
   - Single Responsibility Principle: Each class has a specific purpose
   - Interface Segregation: Separate interfaces for different entities
   - Dependency Injection: DAOs are injected into the LibraryManagementSystem

4. **Transaction Management**
   - Proper handling of database transactions
   - Rollback support for failed operations
   - Connection management using try-with-resources

## Features

- Book Management (Add, Update, Delete, View)
- Member Management (Add, Update, Delete, View)
- Borrowing System
  - Borrow books with return date
  - Return books
  - View borrowing history
- Input Validation
- Error Handling
- Transaction Management

## Prerequisites

1. Java 17 or higher
2. PostgreSQL 12 or higher
3. Maven

## Database Setup

1. Create a PostgreSQL database named 'daniel'
2. Update the database connection details in `Main.java`:
   ```java
   String connectionString = "jdbc:postgresql://localhost:5432/daniel";
   String user = "daniel";
   String password = "password";
   ```

3. Create the required tables:
   ```sql
   CREATE TABLE books (
       id SERIAL PRIMARY KEY,
       title VARCHAR(255) NOT NULL,
       author VARCHAR(255) NOT NULL,
       genre VARCHAR(100),
       available_copies INT NOT NULL
   );

   CREATE TABLE members (
       id SERIAL PRIMARY KEY,
       name VARCHAR(255) NOT NULL,
       email VARCHAR(255) UNIQUE NOT NULL,
       phone VARCHAR(20)
   );

   CREATE TABLE borrowing_records (
       record_id SERIAL PRIMARY KEY,
       book_id INT REFERENCES books(id),
       member_id INT REFERENCES members(id),
       borrow_date DATE NOT NULL,
       return_date DATE,
       FOREIGN KEY (book_id) REFERENCES books(id),
       FOREIGN KEY (member_id) REFERENCES members(id)
   );
   ```

## Building and Running

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd library-management-system
   ```

2. Build the project using Maven:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn exec:java -Dexec.mainClass="com.week2assessment.Main"
   ```

## Usage

The system presents a menu-driven interface with the following options:

1. Add Book
2. Update Book
3. Delete Book
4. View All Books
5. Add Member
6. Update Member
7. Delete Member
8. View All Members
9. Borrow Book
10. Return Book
11. View Borrowing Records
12. Exit

Follow the on-screen prompts to perform various operations.

## Error Handling

The system includes comprehensive error handling for:
- Database connection issues
- Invalid input
- Business rule violations
- Transaction failures
- Resource management

## Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a new Pull Request 