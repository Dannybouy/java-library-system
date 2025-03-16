package com.week2assessment;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class LibraryManagementSystem {
    private final BookDAO bookDAO;
    private final MemberDAO memberDAO;
    private final BorrowingRecordsDAO borrowingRecordsDAO;
    private final Connection connection;

    public LibraryManagementSystem(Connection connection) {
        this.connection = connection;
        this.bookDAO = new BookDAOImpl(connection);
        this.memberDAO = new MemberDAOImpl(connection);
        this.borrowingRecordsDAO = new BorrowingRecordsDAOImpl(connection);
    }

    public void addBook(String title, String author, String genre, int availableCopies) throws SQLException {
        Book book = new Book(title, author, genre, availableCopies);
        bookDAO.addBook(book);
    }

    public void updateBook(int bookId, String title, String author, String genre, Integer availableCopies) throws SQLException {
        Book book = bookDAO.getBookById(bookId);
        if (book == null) {
            throw new SQLException("Book not found with ID: " + bookId);
        }

        if (title != null && !title.isEmpty()) book.setTitle(title);
        if (author != null && !author.isEmpty()) book.setAuthor(author);
        if (genre != null && !genre.isEmpty()) book.setGenre(genre);
        if (availableCopies != null) book.setAvailableCopies(availableCopies);

        bookDAO.updateBook(book);
    }

    public void deleteBook(int bookId) throws SQLException {
        bookDAO.deleteBook(bookId);
    }

    public List<Book> getAllBooks() throws SQLException {
        return bookDAO.getAllBooks();
    }

    public void addMember(String name, String email, String phone) throws SQLException {
        Member member = new Member(name, email, phone);
        memberDAO.addMember(member);
    }

    public void updateMember(int memberId, String name, String email, String phone) throws SQLException {
        Member member = memberDAO.getMemberById(memberId);
        if (member == null) {
            throw new SQLException("Member not found with ID: " + memberId);
        }

        if (name != null && !name.isEmpty()) member.setName(name);
        if (email != null && !email.isEmpty()) member.setEmail(email);
        if (phone != null && !phone.isEmpty()) member.setPhone(phone);

        memberDAO.updateMember(member);
    }

    public void deleteMember(int memberId) throws SQLException {
        memberDAO.deleteMember(memberId);
    }

    public List<Member> getAllMembers() throws SQLException {
        return memberDAO.getAllMembers();
    }

    public void borrowBook(int memberId, int bookId, Date returnDate) throws SQLException {
        try {
            connection.setAutoCommit(false);
            
            Member member = memberDAO.getMemberById(memberId);
            if (member == null) {
                throw new SQLException("Member not found with ID: " + memberId);
            }

            Book book = bookDAO.getBookById(bookId);
            if (book == null) {
                throw new SQLException("Book not found with ID: " + bookId);
            }

            if (book.getAvailableCopies() <= 0) {
                throw new SQLException("No copies available for book: " + book.getTitle());
            }

            // Update book copies
            book.setAvailableCopies(book.getAvailableCopies() - 1);
            bookDAO.updateBook(book);

            // Create borrowing record
            BorrowingRecords record = new BorrowingRecords(bookId, memberId, new Date(), returnDate);
            borrowingRecordsDAO.addBorrowingRecord(record);

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void returnBook(int bookId) throws SQLException {
        try {
            connection.setAutoCommit(false);
            borrowingRecordsDAO.returnBook(bookId);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public List<BorrowingRecords> getAllBorrowingRecords() throws SQLException {
        return borrowingRecordsDAO.getAllBorrowingRecords();
    }
} 