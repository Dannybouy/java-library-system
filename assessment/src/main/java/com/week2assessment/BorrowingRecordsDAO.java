package com.week2assessment;

import java.util.List;

public interface BorrowingRecordsDAO {
    public void addBorrowingRecord(BorrowingRecords borrowingRecord);
    public void returnBook(int bookId);
    public List<BorrowingRecords> getAllBorrowingRecords();
}
