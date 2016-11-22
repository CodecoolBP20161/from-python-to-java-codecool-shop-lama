package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.DatabaseConnection;

/**
 * Created by cave on 2016.11.22..
 */
public class SupplierDaoJdbc {
    private static SupplierDaoJdbc instance;

    private static DatabaseConnection databaseConnection;

    public static SupplierDaoJdbc getInstance() {
        if (instance == null) {
            instance = SupplierDaoJdbc.getInstance();
            databaseConnection = DatabaseConnection.getInstance();
        }
        return instance;
    }

    public static SupplierDaoJdbc getInstance(DatabaseConnection dbConnection) {
        if (instance == null) {
            instance = SupplierDaoJdbc.getInstance();
            databaseConnection = dbConnection;
        }
        return instance;
    }

    private SupplierDaoJdbc() {
    }
}
