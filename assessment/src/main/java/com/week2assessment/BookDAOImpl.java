package com.week2assessment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAOImpl implements BookDAO {
    
    private final Connection connection;

    public BookDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addBook(Book book) {
       String sqlQuery = "INSERT INTO books (title, author, genre, available_copies) VALUES (?, ?, ?, ?)";
       try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
        preparedStatement.setString(1, book.getTitle());
        preparedStatement.setString(2, book.getAuthor());
        preparedStatement.setString(3, book.getGenre());
        preparedStatement.setInt(4, book.getAvailableCopies());
        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Book added successfully");
        } else {
            System.out.println("Failed to add book");
        }
       } catch (SQLException e) {
        e.printStackTrace();
       }
    }

    @Override
    public void updateBook(Book book) {
        String sqlQuery = "UPDATE books SET title = ?, author = ?, genre = ?, available_copies = ? WHERE book_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getGenre());
            preparedStatement.setInt(4, book.getAvailableCopies());
            preparedStatement.setInt(5, book.getBookId());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book updated successfully");
            } else {
                System.out.println("Failed to update book");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteBook(int bookId) {
        String sqlQuery = "DELETE FROM books WHERE book_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setInt(1, bookId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book deleted successfully");
            } else {
                System.out.println("Failed to delete book");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Book> searchBooks(String title, String author, String genre) {
        List<Book> books = new ArrayList<>();
        String sqlQuery = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ? OR genre LIKE ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, "%" + title + "%");
            preparedStatement.setString(2, "%" + author + "%");
            preparedStatement.setString(3, "%" + genre + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                books.add(new Book(resultSet.getInt("book_id"), resultSet.getString("title"), resultSet.getString("author"), resultSet.getString("genre"), resultSet.getInt("available_copies")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public Book getBookById(int bookId) {
        String sqlQuery = "SELECT * FROM books WHERE book_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setInt(1, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Book(resultSet.getInt("book_id"), resultSet.getString("title"), resultSet.getString("author"), resultSet.getString("genre"), resultSet.getInt("available_copies"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sqlQuery = "SELECT * FROM books";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                books.add(new Book(resultSet.getInt("book_id"), resultSet.getString("title"), resultSet.getString("author"), resultSet.getString("genre"), resultSet.getInt("available_copies")));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

}
