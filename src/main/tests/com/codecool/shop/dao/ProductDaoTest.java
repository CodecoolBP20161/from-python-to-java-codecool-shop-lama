package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.ProductDaoJdbc;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.DatabaseConnection;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by cave on 2016.11.21..
 */
@RunWith(Parameterized.class)
public class ProductDaoTest {

    private ProductDao implementation;
    private Product product;
    private Product product2;
    private ProductCategory productCategory = new ProductCategory("test", "test", "test");
    private ProductCategory productCategory2 = new ProductCategory("test2", "test2", "test2");
    private Supplier supplier = new Supplier("test", "test");
    private Supplier supplier2 = new Supplier("test2", "test2");
    private static Connection baseConnection;
    private static DatabaseConnection testDatabaseConnection = DatabaseConnection
            .getInstance("src/main/resources/properties/test_db_config.properties");
    private static Connection testConnection;

    public ProductDaoTest(ProductDao implementation) {
        this.implementation = implementation;
        try {
            baseConnection = DatabaseConnection
                    .getInstance("src/main/resources/properties/db_config.properties").getConnection();
            testConnection = testDatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Parameterized.Parameters
    public static Collection<Object[]> instancesToTest() {
        return Arrays.asList(new Object[][] {
                {ProductDaoMem.getInstance()},
                {ProductDaoJdbc.getInstance()}
        });
    }

    @Before
    public void setUp() throws Exception {
        product = new Product("test", 1.0f, "HUF", "test", productCategory, supplier, "test");
        product2 = new Product("test2", 2.0f, "HUF", "test2", productCategory2, supplier2, "test2");

        if (testConnection != null) {
            deleteTableRows();
            insertNecessarySuppliers();
            insertNecessaryCategories();
        }
    }

    @Test
    public void testDbConnection() throws Exception {
        assertNotNull("valid database name check", baseConnection);
    }

    @Test
    public void getAll() throws Exception {
        implementation.add(product);
        implementation.add(product2);
        List<Product> expectedProducts = new ArrayList<>(Arrays.asList(product, product2));

        List<Product> resultProducts = implementation.getAll();

        assertEquals(
                "check first ProductCategory object",
                expectedProducts.get(0).getName(),
                resultProducts.get(0).getName()
        );
        assertEquals(
                "check second ProductCategory object",
                expectedProducts.get(1).getName(),
                resultProducts.get(1).getName()
        );
    }

    @Test
    public void addNewProduct() throws Exception {

        implementation.add(product);

        assertEquals(
                "is productCategory in the list",
                product.getName(),
                implementation.getAll().get(0).getName());
    }

    @Test
    public void removeProduct() throws Exception {
        implementation.add(product);
        implementation.add(product2);
        List<Product> productsExpected = implementation.getAll();

        implementation.remove(productsExpected.get(1).getId());
        List<Product> productsResult = implementation.getAll();

        assertEquals("row number", 1, productsResult.size());
        assertEquals("was the right one deleted?", "test", productsResult.get(0).getName());
    }


    @Test
    public void find() throws Exception {
        implementation.add(product);
        implementation.add(product2);
        List<Product> productsExpected = implementation.getAll(); // for setting ID

        Product foundedProduct = implementation.find(productsExpected.get(1).getId());

        assertEquals("find productCategory by ID", productsExpected.get(1).getId(), foundedProduct.getId());
    }

    @Test
    public void getByProductCategory() throws Exception {
        implementation.add(product);
        implementation.add(product2);
        List<Product> allProducts = implementation.getAll(); // get ID

        List<Product> foundedProducts = implementation.getBy(allProducts.get(1).getProductCategory());

        assertEquals("list length", 1, foundedProducts.size());
        assertEquals("first elem match?", product2.getName(), foundedProducts.get(0).getName());
    }

    @Test
    public void getBySupplier() throws Exception {
        implementation.add(product);
        implementation.add(product2);
        List<Product> allProducts = implementation.getAll(); // get ID

        List<Product> foundedProducts = implementation.getBy(allProducts.get(1).getProductCategory());

        assertEquals("list length", 1, foundedProducts.size());
        assertEquals("first elem match?", product2.getName(), foundedProducts.get(0).getName());
    }

    @Test
    public void isProductGetSupplierObject() throws Exception {
        implementation.add(product);
        implementation.add(product2);

        List<Product> products = implementation.getAll();

        assertEquals(
                "check first supplier object",
                product.getSupplier().getName(),
                products.get(0).getSupplier().getName()
        );
        assertEquals(
                "check second supplier object",
                product2.getSupplier().getName(),
                products.get(1).getSupplier().getName()
        );
    }

    @Test
    public void isProductGetProductCategoryObject() throws Exception {
        implementation.add(product);
        implementation.add(product2);

        List<Product> products = implementation.getAll();

        assertEquals(
                "check first ProductCategory object",
                product.getProductCategory().getName(),
                products.get(0).getProductCategory().getName()
        );
        assertEquals(
                "check second ProductCategory object",
                product2.getProductCategory().getName(),
                products.get(1).getProductCategory().getName()
        );
    }

    @After
    public void tearDown() throws Exception {
        implementation.getAll().clear();
        if (testConnection != null) {
            deleteTableRows();
        }
    }

    private void deleteTableRows() {
        try {
            Statement statement = testConnection.createStatement();
            statement.execute("DELETE FROM products;");
            statement.execute("DELETE FROM product_categories;");
            statement.execute("DELETE FROM suppliers;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertNecessarySuppliers() {
        try {
            Statement statement = testConnection.createStatement();
            statement.execute("INSERT INTO suppliers (name, description) VALUES ('test', 'test');");
            statement.execute("INSERT INTO suppliers (name, description) VALUES ('test2', 'test2');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertNecessaryCategories() {
        try {
            Statement statement = testConnection.createStatement();
            statement.execute("INSERT INTO product_categories (name, description, department)" +
                    "VALUES ('test', 'test', 'test');");
            statement.execute("INSERT INTO product_categories (name, description, department)" +
                    "VALUES ('test2', 'test2', 'test2');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}