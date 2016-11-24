package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.ProductCategoryDaoJdbc;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.model.DatabaseConnection;
import com.codecool.shop.model.ProductCategory;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by cave on 2016.11.21..
 */
@RunWith(Parameterized.class)
public class ProductCategoryDaoTest {

    private ProductCategoryDao implementation;
    private ProductCategory productCategory;
    private ProductCategory productCategory2;
    private static Connection baseConnection;
    private static DatabaseConnection testDatabaseConnection = DatabaseConnection
            .getInstance("src/main/resources/properties/test_db_config.properties");
    private static Connection testConnection;

    public ProductCategoryDaoTest(ProductCategoryDao implementation) {
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
                {ProductCategoryDaoMem.getInstance()},
                {ProductCategoryDaoJdbc.getInstance()}
        });
    }

    @Before
    public void setUp() throws Exception {
        productCategory = new ProductCategory("test", "test", "test");
        productCategory2 = new ProductCategory("test2", "test2", "test2");

        if (testConnection != null) {
            deleteTableRows();

        }
    }

    @Test
    public void testDbConnection() throws Exception {
        assertNotNull("valid database name check", baseConnection);
    }

    @Test
    public void getAll() throws Exception {
        implementation.add(productCategory);
        implementation.add(productCategory2);
        List<ProductCategory> expectedProductCategories = new ArrayList<>(Arrays.asList(productCategory, productCategory2));

        List<ProductCategory> resultProductCategories = implementation.getAll();

        assertEquals(
                "check first ProductCategory object",
                expectedProductCategories.get(0).getName(),
                resultProductCategories.get(0).getName()
        );
        assertEquals(
                "check second ProductCategory object",
                expectedProductCategories.get(1).getName(),
                resultProductCategories.get(1).getName()
        );
    }

    @Test
    public void addNewProductCategory() throws Exception {

        implementation.add(productCategory);

        assertEquals(
                "is productCategory in the list",
                productCategory.getName(),
                implementation.getAll().get(0).getName());
    }

    @Test
    public void removeProductCategory() throws Exception {
        implementation.add(productCategory);
        implementation.add(productCategory2);
        List<ProductCategory> productCategoriesExpected = implementation.getAll();

        implementation.remove(productCategoriesExpected.get(1).getId());
        List<ProductCategory> productCategoriesResult = implementation.getAll();

        assertEquals("row number", 1, productCategoriesResult.size());
        assertEquals("was the right one deleted?", "test", productCategoriesResult.get(0).getName());
    }


    @Test
    public void find() throws Exception {
        implementation.add(productCategory);
        implementation.add(productCategory2);
        List<ProductCategory> productCategories = implementation.getAll(); // for setId

        ProductCategory foundedProductCategory = implementation.find(productCategories.get(1).getId());

        assertEquals("find productCategory by ID", productCategories.get(1).getId(), foundedProductCategory.getId());
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}