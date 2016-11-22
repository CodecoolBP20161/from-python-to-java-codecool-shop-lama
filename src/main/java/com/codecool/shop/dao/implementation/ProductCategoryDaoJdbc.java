package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.DatabaseConnection;

/**
 * Created by cave on 2016.11.22..
 */
public abstract class ProductCategoryDaoJdbc implements ProductCategoryDao {
    private static ProductCategoryDaoJdbc instance;

    private static DatabaseConnection databaseConnection;

    public static ProductCategoryDaoJdbc getInstance() {
        if (instance == null) {
            instance = ProductCategoryDaoJdbc.getInstance();
            databaseConnection = DatabaseConnection.getInstance();
        }
        return instance;
    }

    public static ProductCategoryDaoJdbc getInstance(DatabaseConnection dbConnection) {
        if (instance == null) {
            instance = ProductCategoryDaoJdbc.getInstance();
            databaseConnection = dbConnection;
        }
        return instance;
    }

    private ProductCategoryDaoJdbc() {
    }
}
