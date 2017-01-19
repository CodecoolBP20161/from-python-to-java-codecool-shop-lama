package com.codecool.shop.user_service;

import com.codecool.shop.model.DatabaseConnection;
import com.codecool.shop.user_service.controller.UserServiceController;

import java.sql.Connection;
import java.sql.SQLException;

import static spark.Spark.*;

/**
 * Created by leviathan on 2016.12.07..
 */
public class UserService {
    private static Connection databaseConnection;
    private static UserServiceController serviceController = new UserServiceController();

    public static void main(String[] args) {
        try {
            databaseConnection = DatabaseConnection.getInstance("src/main/resources/properties/db_config.properties").getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        setup(args);

        // --- MAPPINGS ---
//        get("/checkemail", (request, response) -> {
//            return checkEmail(request.queryParams("email"));
//        });
//        get("/checkuser", (request, response) -> {
//            return checkUserName(request.queryParams("user_name"));
//        });

        UserService application = new UserService();

        get("/api/validate-user", (application.serviceController::loginValidation));
        get("/status", ((request, response) -> "ok"));
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

//    private static boolean checkUserName(String userName){
//        try {
//
//            PreparedStatement preparedStatement = databaseConnection
//                    .prepareStatement("SELECT count(*) FROM users WHERE user_name = ?;");
//            preparedStatement.setString(1, userName);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()){
//                return (resultSet.getInt(1) == 0);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return true;
//    }
//
//    private static boolean checkEmail(String email){
//        try {
//
//            PreparedStatement preparedStatement = databaseConnection
//                    .prepareStatement("SELECT count(*) FROM users WHERE email = ?;");
//            preparedStatement.setString(1, email);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()){
//                return (resultSet.getInt(1) == 0);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return true;
//    }
}
