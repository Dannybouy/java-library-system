package com.week2assessment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BorrowingRecordsDAOImpl implements BorrowingRecordsDAO {
    private final Connection connection;

    public BorrowingRecordsDAOImpl(Connection connection) 
    {
        this.connection = connection;
    }
    
    @Override
    public void addBorrowingRecord(BorrowingRecords borrowingRecord) {
        String sqlQuery = "INSERT INTO borrowing_records (book_id, member_id, borrow_date, return_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sqlQuery)) {
            pstmt.setInt(1, borrowingRecord.getBookId());
            pstmt.setInt(2, borrowingRecord.getMemberId());
            pstmt.setDate(3, new java.sql.Date(borrowingRecord.getBorrowDate().getTime()));
            pstmt.setDate(4, new java.sql.Date(borrowingRecord.getReturnDate().getTime()));
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Borrowing record added successfully");
            } else {
                System.out.println("Failed to add borrowing record");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void returnBook(int bookId) {
        String sqlQuery = "UPDATE books SET available_copies = available_copies + 1 WHERE book_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sqlQuery)){
            pstmt.setInt(1, bookId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book returned successfully");
            } else {
                System.out.println("Failed to return book");
            }
        } catch (SQLException e) {
            System.out.println("Error returning book");
            e.printStackTrace();
        }
    }

    @Override
    public List<BorrowingRecords> getAllBorrowingRecords() {
        List<BorrowingRecords> borrowingRecords = new ArrayList<>();
        String sqlQuery = "SELECT * FROM borrowing_records";
        try (PreparedStatement pstmt = connection.prepareStatement(sqlQuery)){
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                borrowingRecords.add(new BorrowingRecords(rs.getInt("record_id"), rs.getInt("book_id"), rs.getInt("member_id"), rs.getDate("borrow_date"), rs.getDate("return_date")));
            }
        } catch (SQLException e) {
            System.out.println("Error getting all borrowing records");
            e.printStackTrace();
        }
        return borrowingRecords;
    }
}
