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
    public Product find(int id){
        Connection connection = null;
        try {
            connection = databaseConnection.getConnection();
            String cSQL = "SELECT * FROM PRODUCT WHERE ID =" + id;
            ResultSet result = connection.createStatement().executeQuery(cSQL);
            return createInstance(result);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void remove(int id) throws SQLException {}


    @Override
    public List<Product> getAll(){

        String cSQL = "SELECT * FROM PRODUCT";
        try {
            return InstanceList(cSQL);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Product> getBy(Supplier supplier){
        String cSQL = "SELECT * FROM PRODUCT where supplier =" + supplier.getId();
        try {
            return InstanceList(cSQL);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory){
        String cSQL = "SELECT * FROM PRODUCT where category =" + productCategory.getId();
        try {
            return InstanceList(cSQL);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
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

        //TODO: make currency right
        String currencyString = "USD";

//        TODO: add product and supplier instances, when they're ready
        String categoryQuery = result.getString("category");
        String supplierQuery = result.getString("supplier");
        ProductCategory category = null;
        Supplier supplier = null;
        return new Product(name, defaultPrice, currencyString, description, category, supplier);
    }
}
