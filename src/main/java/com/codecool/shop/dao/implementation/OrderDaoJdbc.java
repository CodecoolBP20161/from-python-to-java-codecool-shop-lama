package com.codecool.shop.dao.implementation;

import com.codecool.shop.customer.Address;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.DatabaseConnection;
import com.codecool.shop.order.implementation.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by annakertesz on 12/6/16.
 */
public class OrderDaoJdbc implements OrderDao{

    private static OrderDaoJdbc instance;
    private static Connection connection;

    public static OrderDaoJdbc getInstance() {
        if (instance == null) {
            instance = new OrderDaoJdbc();
            try {
                connection = DatabaseConnection.getInstance("src/main/resources/properties/db_config.properties").getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public static OrderDaoJdbc getInstance(DatabaseConnection dbConnection) {
        if (instance == null) {
            try {
                connection = dbConnection.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            instance = new OrderDaoJdbc();
        }
        return instance;
    }

    private OrderDaoJdbc() {
    }

    @Override
    public void add(Order order) {

    }

    @Override
    public void updateStatus(Order order) {
        try(PreparedStatement preparedStatement = connection
                .prepareStatement("UPDATE orders SET status = ? WHERE id = ?;")) {
            preparedStatement.setString(1, order.getStatus());
            preparedStatement.setInt(2, order.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateShippingAddress(Address address, Order order) {
        try(PreparedStatement preparedStatement = connection
                .prepareStatement("UPDATE orders SET shipping_address = ? WHERE id = ?;")) {
            preparedStatement.setInt(1, address.getId());
            preparedStatement.setInt(2, order.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateBillingAddress(Address address, Order order) {
        try(PreparedStatement preparedStatement = connection
                .prepareStatement("UPDATE orders SET billing_address = ? WHERE id = ?;")) {
            preparedStatement.setInt(1, address.getId());
            preparedStatement.setInt(2, order.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
