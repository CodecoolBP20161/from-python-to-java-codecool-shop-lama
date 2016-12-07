package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.CustomerDao;
import com.codecool.shop.model.DatabaseConnection;
import com.codecool.shop.model.customer.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by prezi on 2016. 12. 07..
 */
public class CustomerDaoJdbc implements CustomerDao {

    private static Connection databaseConnection;
    private static CustomerDaoJdbc INSTANCE;
    private static String DB_PROPERTIES_PATH = "src/main/resources/properties/db_config.properties";

    public static CustomerDaoJdbc getInstance(DatabaseConnection dbConnection) {

        if (INSTANCE == null) {
            INSTANCE = new CustomerDaoJdbc();
            try {
                databaseConnection = dbConnection.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return INSTANCE;
    }


    public static CustomerDaoJdbc getInstance() {

        if (INSTANCE == null) {
            INSTANCE = new CustomerDaoJdbc();
            try {
                databaseConnection = DatabaseConnection.getInstance(DB_PROPERTIES_PATH).getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return INSTANCE;
    }

    private CustomerDaoJdbc() {
    }

    @Override
    public void saveCustomer(Customer customer) {
        String sql = "INSERT INTO products (name, email, phoneNumber, billingAddress, shippingAddress) ";
        String values = "VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement prepStmnt = databaseConnection.prepareStatement(sql + values);
            prepStmnt.setString(1, customer.getName());
            prepStmnt.setString(1, customer.getEmail());
            prepStmnt.setString(1, customer.getPhoneNumber());
            prepStmnt.setInt(1, customer.getBillingAddressID());
            prepStmnt.setInt(1, customer.getShippingAddressID());
            prepStmnt.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
