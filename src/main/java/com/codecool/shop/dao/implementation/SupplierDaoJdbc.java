package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.DatabaseConnection;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDaoJdbc implements SupplierDao{

    private static SupplierDaoJdbc instance;
    private static DatabaseConnection databaseConnection;

    public static SupplierDaoJdbc getInstance() {
        if (instance == null) {
            instance = new SupplierDaoJdbc();
            databaseConnection = DatabaseConnection.getInstance();
        }
        return instance;
    }

    public static SupplierDaoJdbc getInstance(DatabaseConnection dbConnection) {
        if (instance == null) {
            instance = new SupplierDaoJdbc();
            databaseConnection = dbConnection;
        }
        return instance;
    }

    private SupplierDaoJdbc() {
    }

    @Override
    public void add(Supplier supplier) {
        try {
            PreparedStatement preparedStatement = databaseConnection.getConnection()
                    .prepareStatement("INSERT INTO suppliers (name, description) VALUES (?, ?);");
            preparedStatement.setString(1, supplier.getName());
            preparedStatement.setString(2, supplier.getDescription());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Supplier find(int id) {
        try {
            PreparedStatement preparedStatement = databaseConnection.getConnection()
                    .prepareStatement("SELECT * FROM suppliers WHERE id = ?;");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Supplier supplier = new Supplier(resultSet.getString("name"),
                        resultSet.getString("description"));
                supplier.setId(resultSet.getInt("id"));
                return supplier;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(int id) {
        try {
            PreparedStatement preparedStatement = databaseConnection.getConnection()
                    .prepareStatement("DELETE FROM suppliers WHERE id = ?;");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Supplier> getAll() {
        List<Supplier> DATA = new ArrayList<>();
        String query = "SELECT * FROM suppliers;";
        try (Connection connection = databaseConnection.getConnection();
             Statement statement =connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ){
            while (resultSet.next()){
                Supplier actSupplier = new Supplier(resultSet.getString("name"),
                        resultSet.getString("description"));
                actSupplier.setId(resultSet.getInt("id"));
                DATA.add(actSupplier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return DATA;
    }
}
