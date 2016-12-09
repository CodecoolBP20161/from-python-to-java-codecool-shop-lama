package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.DatabaseConnection;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.Status;
import com.codecool.shop.model.customer.Customer;
import com.codecool.shop.order.implementation.Order;

import java.sql.*;

/**
 * Created by annakertesz on 12/6/16.
 */
public class OrderDaoJdbc implements OrderDao{

    private static Connection databaseConnection;
    private static OrderDaoJdbc productDaoJdbc;


    public static OrderDaoJdbc getInstance(DatabaseConnection dbConnection) {

        if (productDaoJdbc == null) {
            productDaoJdbc = new OrderDaoJdbc();
            try {
                databaseConnection = dbConnection.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return productDaoJdbc;
    }


    public static OrderDaoJdbc getInstance() {

        if (productDaoJdbc == null) {
            productDaoJdbc = new OrderDaoJdbc();
            try {
                databaseConnection = DatabaseConnection.getInstance("src/main/resources/properties/db_config.properties").getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return productDaoJdbc;
    }

    private OrderDaoJdbc() {
    }

    @Override
    public void addOrder(Order order) {
        try {
            PreparedStatement preparedStatement = databaseConnection
                    .prepareStatement("INSERT INTO orders (timeStamp, status, order_uuid) " +
                            "VALUES (?, ?, ?)");
            preparedStatement.setTimestamp(1, getTimeStamp());
            preparedStatement.setString(2, String.valueOf(order.getStatus()));
            preparedStatement.setString(3, order.getOrderUUID());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();}
    }

    private Timestamp getTimeStamp(){
        return new Timestamp(System.currentTimeMillis());
    }

    @Override
    public void updateStatus(Order order) {
        try(PreparedStatement preparedStatement = databaseConnection
                .prepareStatement("UPDATE orders SET status = ? WHERE id = ?;")) {
            preparedStatement.setString(1, String.valueOf(order.getStatus()));
            preparedStatement.setInt(2, order.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    //TODO
    @Override
    public void updateShippingAddress(int addressId, Order order) {
            try(PreparedStatement preparedStatement = databaseConnection
                    .prepareStatement("UPDATE orders SET shipping_address = ? WHERE id = ?;")) {
                preparedStatement.setInt(1, addressId);
                preparedStatement.setInt(2, order.getId());
                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
//TODO
    @Override
    public void updateBillingAddress(int addressId, Order order) {
        try(PreparedStatement preparedStatement = databaseConnection
                .prepareStatement("UPDATE orders SET billing_address = ? WHERE id = ?;")) {
            preparedStatement.setInt(1, addressId);
            preparedStatement.setInt(2, order.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void AddLineItem(Order order, Product product){
        try {
            PreparedStatement preparedStatement = databaseConnection
                    .prepareStatement("INSERT INTO order_product_connection (order_id, product_id, quantity) " +
                            "VALUES (?, ?, ?)");
            preparedStatement.setInt(1, order.getID());
            preparedStatement.setInt(2, product.getId());
            preparedStatement.setInt(3, 1);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();}
    }

    @Override
    public void updateCustomer(Customer customer, Order order) {
        try(PreparedStatement preparedStatement = databaseConnection
                .prepareStatement("UPDATE orders SET customer = ? WHERE id = ?;")) {
            preparedStatement.setInt(1, customer.getId());
            preparedStatement.setInt(2, order.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateAddress() {

    }

    @Override
    public Order find(String orderUUID) {
        try (PreparedStatement preparedStatement = databaseConnection
                .prepareStatement("SELECT * FROM orders WHERE order_uuid = ?;")){
            preparedStatement.setString(1, orderUUID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Order order = new Order();
                order.setId(resultSet.getInt("id"));
                order.setOrderUUID(resultSet.getString("order_uuid"));
                order.setStatus(Status.valueOf(resultSet.getString("status")));
                return order;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void removeProductFromOrder(Order order, int productId) {
        try (PreparedStatement preparedStatement = databaseConnection
                .prepareStatement("DELETE FROM order_product_connection WHERE order_id = ? AND product_id = ?;")){
            preparedStatement.setInt(1, order.getID());
            preparedStatement.setInt(2, productId);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changeQuantity(Order order, int productId, int changeBy) {
        try (PreparedStatement preparedStatement = databaseConnection
                .prepareStatement("UPDATE order_product_connection SET quantity = quantity + ? WHERE (order_id = ? AND product_id = ?);")){
            preparedStatement.setInt(1, changeBy);
            preparedStatement.setInt(2, order.getID());
            preparedStatement.setInt(3, productId);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
