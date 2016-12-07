package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.CustomerDao;
import com.codecool.shop.model.DatabaseConnection;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.model.customer.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by leviathan on 2016.12.07..
 */
public class CustomerDaoJdbc implements CustomerDao {
    private static CustomerDaoJdbc instance;
    private static Connection databaseConnection;

    public static CustomerDaoJdbc getInstance() {
        if (instance == null) {
            instance = new CustomerDaoJdbc();
            try {
                databaseConnection = DatabaseConnection.getInstance("src/main/resources/properties/db_config.properties").getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public static CustomerDaoJdbc getInstance(DatabaseConnection dbConnection) {
        if (instance == null) {
            instance = new CustomerDaoJdbc();
            try {
                databaseConnection = dbConnection.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    private CustomerDaoJdbc() {
    }

    @Override
    public void add(Customer customer) {
        try {
            PreparedStatement preparedStatement = databaseConnection
                    .prepareStatement("INSERT INTO customer (name, email, phone_number, customer_uuid) VALUES (?, ?, ?, ?);");
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getEmail());
            preparedStatement.setString(3, customer.getPhoneNumber());
            preparedStatement.setString(4, customer.getCustomerUUID());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Customer find(int id) {
        try {
            PreparedStatement preparedStatement = databaseConnection
                    .prepareStatement("SELECT * FROM customer WHERE id = ?;");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Customer customer = new Customer(resultSet.getString("name"),
                        resultSet.getString("email"), resultSet.getString("phoneNumber"));
                customer.setId(resultSet.getInt("id"));
                customer.setCustomerUUID(resultSet.getString("customer_uuid"));
                return customer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Customer find(String customerUUID) {
        try {
            PreparedStatement preparedStatement = databaseConnection
                    .prepareStatement("SELECT * FROM customer WHERE customer_uuid = ?;");
            preparedStatement.setString(1, customerUUID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Customer customer = new Customer(resultSet.getString("name"),
                        resultSet.getString("email"), resultSet.getString("phone_number"));
                customer.setId(resultSet.getInt("id"));
                return customer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(int id) {
        try {
            PreparedStatement preparedStatement = databaseConnection
                    .prepareStatement("DELETE FROM customer WHERE id = ?;");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Customer> getAll() {
        List<Customer> DATA = new ArrayList<>();
        String query = "SELECT * FROM customer;";
        try (Statement statement = databaseConnection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ){
            while (resultSet.next()){
                Customer actCustomer = new Customer(resultSet.getString("name"),
                        resultSet.getString("email"), resultSet.getString("phoneNumber"));
                actCustomer.setId(resultSet.getInt("id"));
                DATA.add(actCustomer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return DATA;
    }
}
