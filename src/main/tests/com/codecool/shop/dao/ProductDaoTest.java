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
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    private Connection connection;
//    private static DatabaseConnection databaseConnectionMock = mock(DatabaseConnection.class);

    public ProductDaoTest(ProductDao implementation) {
        this.implementation = implementation;
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
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
        product = new Product("test", 1.0f, "HUF", "test", productCategory, supplier);
        product2 = new Product("test2", 2.0f, "HUF", "test2", productCategory2, supplier2);

        if (connection != null) {
//            connection.setAutoCommit(false);
//            when(databaseConnectionMock.getConnection()).thenReturn(connection);
            Statement statement = connection.createStatement();
            try {
                statement.execute("DELETE FROM products;");
            } catch (PSQLException e){
                e.printStackTrace();
            }
        }
    }

    @Test
    public void getAll() throws Exception {
        implementation.add(product);
        implementation.add(product2);
        List<Product> expectedProducts = new ArrayList<>(Arrays.asList(product, product2));

        List<Product> resultProducts = implementation.getAll();

        assertEquals("get all products", expectedProducts, resultProducts);
    }

    @Test
    public void addNewProduct() throws Exception {

        implementation.add(product);

        assertTrue("is product in the list", implementation.getAll().contains(product));
    }

    @Test
    public void removeProduct() throws Exception {
        implementation.add(product);
        implementation.add(product2);

        implementation.remove(product2.getId());

        assertFalse("Try to find product", implementation.getAll().contains(product2));
    }


    @Test
    public void find() throws Exception {
        implementation.add(product);
        implementation.add(product2);

        Product foundedProduct = implementation.find(product2.getId());

        assertEquals("find product by ID", product2, foundedProduct);
    }

    @Test
    public void getByProductCategory() throws Exception {
        implementation.add(product);
        implementation.add(product2);
        List<Product> expectedProducts = new ArrayList<>(Arrays.asList(product2));

        List<Product> foundedProducts = implementation.getBy(productCategory2);

        assertEquals(expectedProducts, foundedProducts);
    }

    @Test
    public void getBySupplier() throws Exception {
        implementation.add(product);
        implementation.add(product2);
        List<Product> expectedProducts = new ArrayList<>(Arrays.asList(product2));

        List<Product> foundedProducts = implementation.getBy(supplier2);

        assertEquals(expectedProducts, foundedProducts);
    }

    @After
    public void tearDown() throws Exception {
        implementation.getAll().clear();
        if (connection != null) {
//            connection.rollback();
//            connection.close();
        }
    }
}