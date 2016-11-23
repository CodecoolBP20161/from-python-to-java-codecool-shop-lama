package com.codecool.shop.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String DATABASE = "jdbc:postgresql://localhost:5432/codecoolshop";
    private static final String DB_USER = "cave";
    private static final String DB_PASSWORD = "123456789";

    private static DatabaseConnection dbConnection;

    public static DatabaseConnection getInstance() {
        if (dbConnection == null){
            dbConnection = new DatabaseConnection();
        }
        return dbConnection;
    }

    private DatabaseConnection() {
    }


    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DATABASE,
                DB_USER,
                DB_PASSWORD);
    }
}
