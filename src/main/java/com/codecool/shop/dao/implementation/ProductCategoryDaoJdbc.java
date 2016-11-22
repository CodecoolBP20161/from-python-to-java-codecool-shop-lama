package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.DatabaseConnection;
import com.codecool.shop.model.ProductCategory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class ProductCategoryDaoJdbc implements ProductCategoryDao {

    private static List<ProductCategory> DATA = new ArrayList<>();
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
            instance = new ProductCategoryDaoJdbc();
            databaseConnection = dbConnection;
        }
        return instance;
    }

    private ProductCategoryDaoJdbc() {
        String query = "SELECT * FROM product_categories;";
        try (Connection connection = databaseConnection.getConnection();
             Statement statement =connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ){
            while (resultSet.next()){
                ProductCategory actCategory = new ProductCategory(resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getString(resultSet.getString("department")));
                DATA.add(actCategory);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(ProductCategory category) {
        category.setId(DATA.size() + 1);
        DATA.add(category);
    }

    @Override
    public ProductCategory find(int id) {
        return DATA.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        DATA.remove(find(id));
    }

    @Override
    public List<ProductCategory> getAll() {
        return DATA;
    }
}
