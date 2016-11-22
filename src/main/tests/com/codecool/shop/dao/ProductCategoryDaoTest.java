package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.ProductCategoryDaoJdbc;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoJdbc;
import com.codecool.shop.model.DatabaseConnection;
import com.codecool.shop.model.ProductCategory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.DriverManager;
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
public class ProductCategoryDaoTest {

    private ProductCategoryDao implementation;
    private ProductCategory productCategory;
    private ProductCategory productCategory2;
    private Connection connection;
    private static DatabaseConnection databaseConnectionMock = mock(DatabaseConnection.class);

    public ProductCategoryDaoTest(ProductCategoryDao implementation) {
        this.implementation = implementation;
        try {
            this.connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/codecoolshop",
                    "cave",
                    "123456789");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Parameterized.Parameters
    public static Collection<Object[]> instancesToTest() {
        return Arrays.asList(new Object[][] {
                {ProductCategoryDaoMem.getInstance()},
                {ProductCategoryDaoJdbc.getInstance(databaseConnectionMock)}
        });
    }

    @Before
    public void setUp() throws Exception {
        productCategory = new ProductCategory("test", "test", "test");
        productCategory2 = new ProductCategory("test2", "test2", "test2");

        if (connection != null) {
            connection.setAutoCommit(false);
            when(databaseConnectionMock.getConnection()).thenReturn(connection);
            Statement statement = connection.createStatement();
            try {
                statement.execute("DELETE FROM product_categories;");
            } catch (PSQLException e){
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testDbConnection() throws Exception {
        assertNotNull("valid database name check", connection);
    }



    @Test
    public void getAll() throws Exception {
        implementation.add(productCategory);
        implementation.add(productCategory2);
        List<ProductCategory> expectedProductCategories = new ArrayList<>(Arrays.asList(productCategory, productCategory2));

        List<ProductCategory> resultProductCategories = implementation.getAll();

        assertEquals("get all productCategories", expectedProductCategories, resultProductCategories);
    }

    @Test
    public void addNewProductCategory() throws Exception {

        implementation.add(productCategory);

        assertTrue("is productCategory in the list", implementation.getAll().contains(productCategory));
    }

    @Test
    public void removeProductCategory() throws Exception {
        implementation.add(productCategory);
        implementation.add(productCategory2);

        implementation.remove(productCategory2.getId());

        assertFalse("Try to find productCategory", implementation.getAll().contains(productCategory2));
    }


    @Test
    public void find() throws Exception {
        implementation.add(productCategory);
        implementation.add(productCategory2);

        ProductCategory foundedProductCategory = implementation.find(productCategory2.getId());

        assertEquals("find productCategory by ID", productCategory2, foundedProductCategory);
    }

    @After
    public void tearDown() throws Exception {
        implementation.getAll().clear();
        if (connection != null) {
            connection.rollback();
            connection.close();
        }
    }

}