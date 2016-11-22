package com.codecool.shop.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by cave on 2016.11.21..
 */
public class DatabaseConnection {
    private static final String DATABASE = "jdbc:postgresql://localhost:5432/codecoolshop";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "postgres";

    private static DatabaseConnection dbConnection = new DatabaseConnection();

    public static DatabaseConnection getInstance() {
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
