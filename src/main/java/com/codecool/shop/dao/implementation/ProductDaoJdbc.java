package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.DatabaseConnection;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cave on 2016.11.21..
 */
public class ProductDaoJdbc implements ProductDao {

    private static DatabaseConnection databaseConnection;
    private static ProductDaoJdbc productDaoJdbc;


    public static ProductDaoJdbc getInstance(DatabaseConnection dbConnection) {

        if (productDaoJdbc == null) {
            productDaoJdbc = new ProductDaoJdbc();
            databaseConnection = dbConnection;
        }

        return productDaoJdbc;
    }


    public static ProductDaoJdbc getInstance() {

        if (productDaoJdbc == null) {
            productDaoJdbc = new ProductDaoJdbc();
            databaseConnection = DatabaseConnection.getInstance();

        }

        return productDaoJdbc;
    }

    private ProductDaoJdbc() {
    }

    @Override
    public void add(Product product) {
        try {
            PreparedStatement preparedStatement = databaseConnection.getConnection()
                    .prepareStatement("INSERT INTO products (name, default_price, product_category, supplier, description, default_currency) " +
                            "VALUES (?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, product.getName());
            preparedStatement.setFloat(2, product.getDefaultPrice());
            preparedStatement.setInt(3, queryProductCategory(product.getProductCategory()));
            preparedStatement.setInt(4, querySupplier(product.getSupplier()));
            preparedStatement.setString(5, product.getDescription());
            preparedStatement.setString(6, String.valueOf(product.getDefaultCurrency()));
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int queryProductCategory(ProductCategory productCategory) {
        try {
            PreparedStatement preparedStatement = databaseConnection.getConnection()
                    .prepareStatement("SELECT id FROM product_categories WHERE name = ?;");
            preparedStatement.setString(1, productCategory.getName());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private int querySupplier(Supplier supplier) {
        try {
            PreparedStatement preparedStatement = databaseConnection.getConnection()
                    .prepareStatement("SELECT id FROM suppliers WHERE name = ?;");
            preparedStatement.setString(1, supplier.getName());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public Product find(int id){
        try {
            PreparedStatement preparedStatement = databaseConnection.getConnection()
                    .prepareStatement("SELECT * FROM products WHERE id = ?;");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return createInstance(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(int id) {
        try {
            PreparedStatement preparedStatement = databaseConnection.getConnection()
                    .prepareStatement("DELETE FROM products WHERE id = ?;");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<Product> getAll(){

        String cSQL = "SELECT * FROM PRODUCT";
        try {
            return InstanceList(cSQL);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<Product> getBy(Supplier supplier){
        String cSQL = "SELECT * FROM PRODUCT where supplier =" + supplier.getId();
        try {
            return InstanceList(cSQL);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory){
        String cSQL = "SELECT * FROM PRODUCT where category =" + productCategory.getId();
        try {
            return InstanceList(cSQL);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }



    private List<Product> InstanceList(String sql) throws SQLException {
        List<Product> productList = new ArrayList<>();
        Connection connection = databaseConnection.getConnection();
        ResultSet result = connection.createStatement().executeQuery(sql);
        while (result.next()){
            productList.add(createInstance(result));
        }
        return productList;
    }

    private Product createInstance(ResultSet result) throws SQLException {

        String name = result.getString("name");
        float defaultPrice = result.getInt("default_price");
        String description = result.getString("description");
        String currency = result.getString("currency");
        int categoryID = result.getInt("id");
        int supplierID = result.getInt("id");
        ProductCategory category = ProductCategoryDaoJdbc.getInstance().find(categoryID);
        Supplier supplier = SupplierDaoJdbc.getInstance().find(supplierID);
        return new Product(name, defaultPrice, currency, description, category, supplier);
    }


}
