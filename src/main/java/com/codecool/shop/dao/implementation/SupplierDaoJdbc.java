package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.DatabaseConnection;
import com.codecool.shop.model.Supplier;

import java.util.List;

/**
 * Created by cave on 2016.11.22..
 */
public class SupplierDaoJdbc implements SupplierDao{
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

    @Override
    public void add(Supplier category) {
        
    }

    @Override
    public Supplier find(int id) {
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Supplier> getAll() {
        return null;
    }
}
