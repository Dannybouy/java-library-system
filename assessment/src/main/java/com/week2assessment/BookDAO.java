package com.week2assessment;

import java.util.List;

public interface BookDAO {
    public void addBook(Book book);
    public void updateBook(Book book);
    public void deleteBook(int bookId);
    public List<Book> searchBooks(String title, String author, String genre);
    public Book getBookById(int bookId);
    public List<Book> getAllBooks();
}
