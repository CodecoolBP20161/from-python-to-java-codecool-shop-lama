package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoJdbc implements ProductDao {

    private static Connection databaseConnection;
    private static ProductDaoJdbc productDaoJdbc;


    public static ProductDaoJdbc getInstance(DatabaseConnection dbConnection) {

        if (productDaoJdbc == null) {
            productDaoJdbc = new ProductDaoJdbc();
            try {
                databaseConnection = dbConnection.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return productDaoJdbc;
    }


    public static ProductDaoJdbc getInstance() {

        if (productDaoJdbc == null) {
            productDaoJdbc = new ProductDaoJdbc();
            try {
                databaseConnection = DatabaseConnection.getInstance().getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return productDaoJdbc;
    }

    private ProductDaoJdbc() {
    }

    @Override
    public void add(Product product) {
        try {
            PreparedStatement preparedStatement = databaseConnection
                    .prepareStatement("INSERT INTO products (name, default_price, product_category, supplier, description, default_currency, imageSource) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, product.getName());
            preparedStatement.setFloat(2, product.getDefaultPrice());
            preparedStatement.setInt(3, groupQuery(product.getProductCategory()));
            preparedStatement.setInt(4, groupQuery(product.getSupplier()));
            preparedStatement.setString(5, product.getDescription());
            preparedStatement.setString(6, String.valueOf(product.getDefaultCurrency()));
            preparedStatement.setString(7, product.getImageSource());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int groupQuery(BaseModel baseModel) {
        String group = ((Supplier.class.isInstance(baseModel)) ? "suppliers" : "product_categories");
        try {
            PreparedStatement preparedStatement = databaseConnection
                    .prepareStatement("SELECT id FROM " + group + " WHERE name = ?;");
            preparedStatement.setString(1, baseModel.getName());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public Product find(int id){
        try {
            PreparedStatement preparedStatement = databaseConnection
                    .prepareStatement("SELECT * FROM products WHERE id = ?;");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Product product = createInstance(resultSet);
                return product;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(int id) {
        try {
            PreparedStatement preparedStatement = databaseConnection
                    .prepareStatement("DELETE FROM products WHERE id = ?;");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<Product> getAll(){
        try {
            PreparedStatement preparedStatement = databaseConnection
                    .prepareStatement("SELECT * FROM products;");
            return InstanceList(preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private List<Product> getByBaseModel(BaseModel baseModel){
        String group = ((Supplier.class.isInstance(baseModel)) ? "supplier" : "product_category");
        try {
            PreparedStatement preparedStatement = databaseConnection
                    .prepareStatement("SELECT * FROM products where " + group + " = ?;");
            preparedStatement.setInt(1, baseModel.getId());
            return InstanceList(preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory){
        return getInstance().getByBaseModel(productCategory);
    }

    @Override
    public List<Product> getBy(Supplier supplier){
        return getInstance().getByBaseModel(supplier);
    }



    private List<Product> InstanceList(PreparedStatement preparedStatement) throws SQLException {
        List<Product> productList = new ArrayList<>();
        ResultSet result = preparedStatement.executeQuery();
        while (result.next()){
            productList.add(createInstance(result));
        }
        return productList;
    }

    private Product createInstance(ResultSet result) throws SQLException {

        String name = result.getString("name");
        float defaultPrice = result.getInt("default_price");
        String description = result.getString("description");
        String currency = result.getString("default_currency");
        int categoryID = result.getInt("product_category");
        int supplierID = result.getInt("supplier");
        String image = result.getString("imageSource");
        ProductCategory category = ProductCategoryDaoJdbc.getInstance().find(categoryID);
        Supplier supplier = SupplierDaoJdbc.getInstance().find(supplierID);
        Product product = new Product(name, defaultPrice, currency, description, category, supplier, image);
        product.setId(result.getInt("id"));
        return product;
    }
}
