package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
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
                    .prepareStatement("INSERT INTO products (name, default_price, product_category, supplier, description, default_currency) " +
                            "VALUES (?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, product.getName());
            preparedStatement.setFloat(2, product.getDefaultPrice());
            preparedStatement.setInt(3, groupQuery(product.getProductCategory()));
            preparedStatement.setInt(4, groupQuery(product.getSupplier()));
            preparedStatement.setString(5, product.getDescription());
            preparedStatement.setString(6, String.valueOf(product.getDefaultCurrency()));
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
        ProductCategory category = ProductCategoryDaoJdbc.getInstance().find(categoryID);
        Supplier supplier = SupplierDaoJdbc.getInstance().find(supplierID);
        Product product = new Product(name, defaultPrice, currency, description, category, supplier);
        product.setId(result.getInt("id"));
        return product;
    }

    public static void main(String[] args) {
        ProductDao productDataStore = getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoJdbc.getInstance();
        SupplierDao supplierDataStore = SupplierDaoJdbc.getInstance();
        try {
            System.out.println(productDataStore.getBy(productCategoryDataStore.find(2)));
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        //setting up a new supplier
//        Supplier amazon = new Supplier("Amazon", "Digital content and services");
//        supplierDataStore.add(amazon);
//        Supplier lenovo = new Supplier("Lenovo", "Computers");
//        supplierDataStore.add(lenovo);
//
//        //setting up a new product category
//        ProductCategory tablet = new ProductCategory("Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
//        productCategoryDataStore.add(tablet);
//        ProductCategory phone = new ProductCategory("Phone", "Hardware", "phone");
//        productCategoryDataStore.add(phone);
//        ProductCategory laptop = new ProductCategory("Laptop", "Hardware", "laptop");
//        productCategoryDataStore.add(laptop);
//        ProductCategory headset = new ProductCategory("Headset", "Hardware", "headset");
//        productCategoryDataStore.add(headset);
//
//
//        //setting up products and printing it
//        productDataStore.add(new Product("Amazon Fire", 49.9f, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", tablet, amazon));
//        productDataStore.add(new Product("Lenovo IdeaPad Miix 700", 479, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", tablet, lenovo));
//        productDataStore.add(new Product("Lenovo 5520", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", phone, amazon));
//        productDataStore.add(new Product("Sony Headset Stuff", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", headset, amazon));
//        productDataStore.add(new Product("Apple Ipad", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", tablet, amazon));
//        productDataStore.add(new Product("HP ProBook", 89, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", laptop, lenovo));
//        productDataStore.add(new Product("Nokia 2110", 89,  "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", phone, amazon));
//        productDataStore.add(new Product("Asus ZenBook", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", laptop, lenovo));
    }


}
