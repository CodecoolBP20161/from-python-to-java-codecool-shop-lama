package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.DatabaseConnection;
import com.codecool.shop.model.ProductCategory;

import java.util.List;

/**
 * Created by cave on 2016.11.22..
 */
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
            instance = new ProductCategoryDaoJdbc();
            databaseConnection = dbConnection;
        }
        return instance;
    }

    private ProductCategoryDaoJdbc() {
    }

    @Override
    public void add(ProductCategory category) {

    }

    @Override
    public ProductCategory find(int id) {
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<ProductCategory> getAll() {
        return null;
    }
}
