package com.codecool.shop.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    private static String database;
    private static String dbUser;
    private static String dbPassword;

    private static DatabaseConnection dbConnection;

    public static DatabaseConnection getInstance(String propertiesFile) {
        if (dbConnection == null){
            dbConnection = new DatabaseConnection(propertiesFile);
        }
        return dbConnection;
    }

    public static DatabaseConnection getInstance() {
        if (dbConnection == null){
            dbConnection = new DatabaseConnection();
        }
        return dbConnection;
    }

    private DatabaseConnection() {
    }

    private DatabaseConnection(String propertiesFile) {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            
            input = new FileInputStream(propertiesFile);

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            database = prop.getProperty("database");
            dbUser = prop.getProperty("dbuser");
            dbPassword = prop.getProperty("dbpassword");

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                database,
                dbUser,
                dbPassword);
    }
}
