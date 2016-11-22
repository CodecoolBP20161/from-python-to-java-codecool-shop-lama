package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.DatabaseConnection;
import com.codecool.shop.model.ProductCategory;

import java.sql.*;
import java.util.List;

import java.util.ArrayList;


public class ProductCategoryDaoJdbc implements ProductCategoryDao {

    private static ProductCategoryDaoJdbc instance;
    private static DatabaseConnection databaseConnection;

    public static ProductCategoryDaoJdbc getInstance() {
        if (instance == null) {
            instance = new ProductCategoryDaoJdbc();
            databaseConnection = DatabaseConnection.getInstance();
        }
        return instance;
    }

    public static ProductCategoryDaoJdbc getInstance(DatabaseConnection dbConnection) {
        if (instance == null) {
            databaseConnection = dbConnection;
            instance = new ProductCategoryDaoJdbc();
        }
        return instance;
    }

    private ProductCategoryDaoJdbc() {
    }

    @Override
    public void add(ProductCategory category) {
        try {
            PreparedStatement preparedStatement = databaseConnection.getConnection()
                    .prepareStatement("INSERT INTO product_categories (name, description, department) VALUES (?, ?, ?);");
            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getDescription());
            preparedStatement.setString(3, category.getDepartment());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ProductCategory find(int id) {
        try {
            PreparedStatement preparedStatement = databaseConnection.getConnection()
                    .prepareStatement("SELECT * FROM product_categories WHERE id = ?;");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                ProductCategory category = new ProductCategory(resultSet.getString("name"),
                        resultSet.getString("department"),
                        resultSet.getString("description"));
                category.setId(resultSet.getInt("id"));
                return category;
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
                    .prepareStatement("DELETE FROM product_categories WHERE id = ?;");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ProductCategory> getAll() {
        List<ProductCategory> DATA = new ArrayList<>();
        String query = "SELECT * FROM product_categories;";
        try (Connection connection = databaseConnection.getConnection();
             Statement statement =connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ){
            while (resultSet.next()){
                ProductCategory actCategory = new ProductCategory(resultSet.getString("name"),
                        resultSet.getString("department"),
                        resultSet.getString("description"));
                actCategory.setId(resultSet.getInt("id"));
                DATA.add(actCategory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return DATA;
    }

    private void executeQuery(String query) {
        try (Connection connection = databaseConnection.getConnection();
             Statement statement =connection.createStatement()
        ){
            statement.execute(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
