package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.DatabaseConnection;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.sql.*;
import java.util.List;

/**
 * Created by cave on 2016.11.21..
 */
public class ProductDaoJdbc implements ProductDao {
    private static DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

    private static ProductDaoJdbc productDaoJdbc = new ProductDaoJdbc();

    public static ProductDaoJdbc getInstance() {
        return productDaoJdbc;
    }

    private ProductDaoJdbc() {
    }

    @Override
    public void add(Product product) {
        try {
            PreparedStatement preparedStatement = databaseConnection.getConnection()
                    .prepareStatement("INSERT INTO products (name, default_price, category, supplier, description) " +
                            "VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setString(1, product.getName());
            preparedStatement.setFloat(2, product.getDefaultPrice());
            preparedStatement.setString(3, queryProductCategory(product.getProductCategory()));
            preparedStatement.setString(4, querySupplier(product.getSupplier()));
            preparedStatement.setString(5, product.getDescription());
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String queryProductCategory(ProductCategory productCategory) {
        return "SELECT * FROM product_categories WHERE id ='" + productCategory.getId() + "';";
    }

    private String querySupplier(Supplier supplier) {
        return "SELECT * FROM suppliers WHERE id ='" + supplier.getId() + "';";
    }

    @Override
    public Product find(int id) {
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Product> getAll() {
        return null;
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        return null;
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        return null;
    }
}
