package com.week2assessment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        String connectionString = "jdbc:postgresql://localhost:5432/daniel";
        String user = "daniel";
        String password = "password";

        try (Connection connection = DriverManager.getConnection(connectionString, user, password)) {
            LibraryManagementSystem library = new LibraryManagementSystem(connection);
            LibraryUI ui = new LibraryUI(library);
            ui.start();
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}