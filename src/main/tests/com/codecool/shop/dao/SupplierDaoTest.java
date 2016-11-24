package com.codecool.shop.dao;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.codecool.shop.dao.implementation.SupplierDaoJdbc;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.DatabaseConnection;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.util.*;

/**
 * Created by cave on 2016.11.21..
 */
@RunWith(Parameterized.class)
public class SupplierDaoTest {

    private SupplierDao implementation;
    private Supplier supplier;
    private Supplier supplier2;
    private Connection connection;
//    private static DatabaseConnection databaseConnectionMock = mock(DatabaseConnection.class);


    public SupplierDaoTest(SupplierDao implementation) throws SQLException {
        this.implementation = implementation;
        try {
            this.connection = DatabaseConnection.getInstance("src/main/resources/properties/db_config.properties").getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Parameterized.Parameters
    public static Collection<Object[]> instancesToTest() {
        return Arrays.asList(new Object[][] {
                {SupplierDaoMem.getInstance()},
                {SupplierDaoJdbc.getInstance()}
        });
    }

    @Before
    public void setUp() throws Exception {
        supplier = new Supplier("test", "test");
        supplier2 = new Supplier("test2", "test2");

        if (connection != null) {
//            connection.setAutoCommit(false);
//            when(databaseConnectionMock.getConnection()).thenReturn(connection);
            setupTables();
        }
    }

    @Test
    public void testDbConnection() throws Exception {
        assertNotNull("valid database name check", connection);
    }

    @Test
    public void getAll() throws Exception {
        implementation.add(supplier);
        implementation.add(supplier2);
        List<Supplier> expectedSuppliers = new ArrayList<>(Arrays.asList(supplier, supplier2));

        List<Supplier> resultSuppliers = implementation.getAll();

        assertEquals(
                "check first Supplier object",
                expectedSuppliers.get(0).getName(),
                resultSuppliers.get(0).getName()
        );
        assertEquals(
                "check second Supplier object",
                expectedSuppliers.get(1).getName(),
                resultSuppliers.get(1).getName()
        );
    }

    @Test
    public void addNewSupplier() throws Exception {

        implementation.add(supplier);

        assertEquals(
                "is productCategory in the list",
                supplier.getName(),
                implementation.getAll().get(0).getName());
    }

    @Test
    public void removeSupplier() throws Exception {
        implementation.add(supplier);
        implementation.add(supplier2);
        List<Supplier> suppliersExpected = implementation.getAll(); // for setId

        implementation.remove(suppliersExpected.get(1).getId());
        List<Supplier> suppliersResult = implementation.getAll();

        assertEquals("row number", 1, suppliersResult.size());
        assertEquals("was the right one deleted?", "test", suppliersResult.get(0).getName());
    }


    @Test
    public void find() throws Exception {
        implementation.add(supplier);
        implementation.add(supplier2);
        List<Supplier> suppliers = implementation.getAll(); // for setId

        Supplier foundedSupplier = implementation.find(suppliers.get(1).getId());

        assertEquals("find supplier by ID", suppliers.get(1).getId(), foundedSupplier.getId());
    }

    @After
    public void tearDown() throws Exception {
        implementation.getAll().clear();
        if (connection != null) {
            setupTables();
//            connection.rollback();
//            connection.close();
        }
    }

    private void setupTables() {
        try {
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM products;");
            statement.execute("DELETE FROM suppliers;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}