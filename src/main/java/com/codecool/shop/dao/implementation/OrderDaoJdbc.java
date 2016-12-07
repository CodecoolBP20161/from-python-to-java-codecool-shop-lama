package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.DatabaseConnection;
import com.codecool.shop.model.Product;
import com.codecool.shop.order.implementation.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

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
                    .prepareStatement("INSERT INTO orders (timeStamp, status) " +
                            "VALUES (?, ?)");
            preparedStatement.setTimestamp(1, getTimeStamp());
            preparedStatement.setString(2, String.valueOf(order.getStatus()));
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();}
    }

    private Timestamp getTimeStamp(){
        return new Timestamp(System.currentTimeMillis());
    }

    public void AddLineItem(Order order, Product product){
        try {
            PreparedStatement preparedStatement = databaseConnection
                    .prepareStatement("INSERT INTO order_product_connection (order_id, product_id, quantity) " +
                            "VALUES (?, ?, ?)");
            preparedStatement.setString(1, order.getID());
            preparedStatement.setInt(2, product.getId());
            preparedStatement.setInt(3, 1);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();}
    }



}
