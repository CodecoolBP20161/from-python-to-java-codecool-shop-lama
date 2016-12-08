package com.codecool.user_service;

import com.codecool.shop.model.DatabaseConnection;
import com.codecool.shop.model.customer.Customer;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static spark.Spark.*;

/**
 * Created by leviathan on 2016.12.07..
 */
public class userService {
    private static Connection databaseConnection;

    public static void main(String[] args) {
        try {
            databaseConnection = DatabaseConnection.getInstance("src/main/resources/properties/db_config.properties").getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        setup(new String[]{"60000"});

        // --- MAPPINGS ---
        get("/checkemail", (request, response) -> {
            return checkEmail(request.queryParams("email"));
        });
        get("/checkuser", (request, response) -> {
            return checkUserName(request.queryParams("user_name"));
        });

        // --- EXCEPTION HANDLING ---
        exception(URISyntaxException.class, (exception, request, response) -> {
            response.status(500);
            response.body(String.format("URI building error, maybe wrong format? : %s", exception.getMessage()));
        });

        exception(Exception.class, (exception, request, response) -> {
            response.status(500);
            response.body(String.format("Unexpected error occurred: %s", exception.getMessage()));
        });
    }

    /**
     * Setting up port
     * @param args - app args
     */
    private static void setup(String[] args){
        if(args == null || args.length == 0){
            System.exit(-1);
        }

        try {
            port(Integer.parseInt(args[0]));
        } catch (NumberFormatException e){
            System.exit(-1);
        }
    }

    private static boolean checkUserName(String userName){
        try {

            PreparedStatement preparedStatement = databaseConnection
                    .prepareStatement("SELECT count(*) FROM users WHERE user_name = ?;");
            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                return (resultSet.getInt(1) == 0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    private static boolean checkEmail(String email){
        try {

            PreparedStatement preparedStatement = databaseConnection
                    .prepareStatement("SELECT count(*) FROM users WHERE email = ?;");
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                return (resultSet.getInt(1) == 0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
