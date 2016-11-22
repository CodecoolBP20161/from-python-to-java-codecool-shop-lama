package com.codecool.shop.dao;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.DatabaseConnection;
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
    private static DatabaseConnection databaseConnectionMock = mock(DatabaseConnection.class);


    public SupplierDaoTest(SupplierDao implementation) throws SQLException {
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
                {SupplierDaoMem.getInstance()}
        });
    }

    @Before
    public void setUp() throws Exception {
        supplier = new Supplier("test", "test");
        supplier2 = new Supplier("test2", "test2");

        if (connection != null) {
            connection.setAutoCommit(false);
            when(databaseConnectionMock.getConnection()).thenReturn(connection);
            Statement statement = connection.createStatement();
            try {
                statement.execute("DELETE FROM suppliers;");
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
        implementation.add(supplier);
        implementation.add(supplier2);
        List<Supplier> expectedSuppliers = new ArrayList<>(Arrays.asList(supplier, supplier2));

        List<Supplier> resultSuppliers = implementation.getAll();

        assertEquals("get all suppliers", expectedSuppliers, resultSuppliers);
    }

    @Test
    public void addNewSupplier() throws Exception {

        implementation.add(supplier);

        assertTrue("is supplier in the list", implementation.getAll().contains(supplier));
    }

    @Test
    public void removeSupplier() throws Exception {
        implementation.add(supplier);
        implementation.add(supplier2);

        implementation.remove(supplier2.getId());

        assertFalse("Try to find supplier", implementation.getAll().contains(supplier2));
    }


    @Test
    public void find() throws Exception {
        implementation.add(supplier);
        implementation.add(supplier2);

        Supplier foundedSupplier = implementation.find(supplier2.getId());

        assertEquals("find supplier by ID", supplier2, foundedSupplier);
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