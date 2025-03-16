package com.week2assessment;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class LibraryUI {
    private final Scanner scanner;
    private final LibraryManagementSystem library;
    private final SimpleDateFormat dateFormat;

    public LibraryUI(LibraryManagementSystem library) {
        this.scanner = new Scanner(System.in);
        this.library = library;
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    public void start() {
        boolean isRunning = true;
        while (isRunning) {
            displayMenu();
            int choice = readIntInput("Enter your choice: ");
            scanner.nextLine(); // Consume newline

            try {
                switch (choice) {
                    case 1 -> handleAddBook();
                    case 2 -> handleUpdateBook();
                    case 3 -> handleDeleteBook();
                    case 4 -> handleViewAllBooks();
                    case 5 -> handleAddMember();
                    case 6 -> handleUpdateMember();
                    case 7 -> handleDeleteMember();
                    case 8 -> handleViewAllMembers();
                    case 9 -> handleBorrowBook();
                    case 10 -> handleReturnBook();
                    case 11 -> handleViewBorrowingRecords();
                    case 12 -> {
                        isRunning = false;
                        scanner.close();
                    }
                    default -> System.out.println("Invalid choice! Please try again.");
                }
            } catch (SQLException | ParseException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void displayMenu() {
        System.out.println("\nWelcome to the Library Management System");
        System.out.println("1. Add Book");
        System.out.println("2. Update Book");
        System.out.println("3. Delete Book");
        System.out.println("4. View All Books");
        System.out.println("5. Add Member");
        System.out.println("6. Update Member");
        System.out.println("7. Delete Member");
        System.out.println("8. View All Members");
        System.out.println("9. Borrow Book");
        System.out.println("10. Return Book");
        System.out.println("11. View Borrowing Records");
        System.out.println("12. Exit");
    }

    private void handleAddBook() throws SQLException {
        System.out.println("\nEnter Book Details:");
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Author: ");
        String author = scanner.nextLine();
        System.out.print("Genre: ");
        String genre = scanner.nextLine();
        int copies = readIntInput("Available copies: ");

        library.addBook(title, author, genre, copies);
        System.out.println("Book added successfully!");
    }

    private void handleUpdateBook() throws SQLException {
        int bookId = readIntInput("Enter book ID to update: ");
        scanner.nextLine(); // Consume newline

        System.out.println("Enter new details (press Enter to keep current value):");
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Author: ");
        String author = scanner.nextLine();
        System.out.print("Genre: ");
        String genre = scanner.nextLine();
        System.out.print("Available copies (press Enter to skip): ");
        String copiesStr = scanner.nextLine();
        Integer copies = copiesStr.isEmpty() ? null : Integer.parseInt(copiesStr);

        library.updateBook(bookId, title, author, genre, copies);
        System.out.println("Book updated successfully!");
    }

    private void handleDeleteBook() throws SQLException {
        int bookId = readIntInput("Enter book ID to delete: ");
        library.deleteBook(bookId);
        System.out.println("Book deleted successfully!");
    }

    private void handleViewAllBooks() throws SQLException {
        List<Book> books = library.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("No books found");
            return;
        }

        System.out.println("\nAll Books:");
        System.out.printf("%-5s %-30s %-20s %-15s %-15s%n", "ID", "Title", "Author", "Genre", "Available");
        for (Book book : books) {
            System.out.printf("%-5d %-30s %-20s %-15s %-15d%n",
                book.getBookId(), book.getTitle(), book.getAuthor(),
                book.getGenre(), book.getAvailableCopies());
        }
    }

    private void handleAddMember() throws SQLException {
        System.out.println("\nEnter Member Details:");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Phone: ");
        String phone = scanner.nextLine();

        library.addMember(name, email, phone);
        System.out.println("Member added successfully!");
    }

    private void handleUpdateMember() throws SQLException {
        int memberId = readIntInput("Enter member ID to update: ");
        scanner.nextLine(); // Consume newline

        System.out.println("Enter new details (press Enter to keep current value):");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Phone: ");
        String phone = scanner.nextLine();

        library.updateMember(memberId, name, email, phone);
        System.out.println("Member updated successfully!");
    }

    private void handleDeleteMember() throws SQLException {
        int memberId = readIntInput("Enter member ID to delete: ");
        library.deleteMember(memberId);
        System.out.println("Member deleted successfully!");
    }

    private void handleViewAllMembers() throws SQLException {
        List<Member> members = library.getAllMembers();
        if (members.isEmpty()) {
            System.out.println("No members found");
            return;
        }

        System.out.println("\nAll Members:");
        System.out.printf("%-5s %-20s %-30s %-15s%n", "ID", "Name", "Email", "Phone");
        for (Member member : members) {
            System.out.printf("%-5d %-20s %-30s %-15s%n",
                member.getMemberId(), member.getName(),
                member.getEmail(), member.getPhone());
        }
    }

    private void handleBorrowBook() throws SQLException, ParseException {
        int memberId = readIntInput("Enter member ID: ");
        int bookId = readIntInput("Enter book ID: ");
        System.out.print("Enter return date (YYYY-MM-DD): ");
        String returnDateStr = scanner.nextLine();
        Date returnDate = dateFormat.parse(returnDateStr);

        library.borrowBook(memberId, bookId, returnDate);
        System.out.println("Book borrowed successfully!");
    }

    private void handleReturnBook() throws SQLException {
        int bookId = readIntInput("Enter book ID to return: ");
        library.returnBook(bookId);
        System.out.println("Book returned successfully!");
    }

    private void handleViewBorrowingRecords() throws SQLException {
        List<BorrowingRecords> records = library.getAllBorrowingRecords();
        if (records.isEmpty()) {
            System.out.println("No borrowing records found");
            return;
        }

        System.out.println("\nAll Borrowing Records:");
        System.out.printf("%-5s %-5s %-5s %-12s %-12s%n", 
            "ID", "Book ID", "Member ID", "Borrow Date", "Return Date");
        for (BorrowingRecords record : records) {
            System.out.printf("%-5d %-5d %-5d %-12s %-12s%n",
                record.getRecordId(), record.getBookId(),
                record.getMemberId(),
                dateFormat.format(record.getBorrowDate()),
                dateFormat.format(record.getReturnDate()));
        }
    }

    private int readIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a valid number");
            System.out.print(prompt);
            scanner.next();
        }
        return scanner.nextInt();
    }
} 