package com.codecool.shop.dao;

import static org.junit.Assert.*;

import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Supplier;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

/**
 * Created by cave on 2016.11.21..
 */
@RunWith(Parameterized.class)
public class SupplierDaoTest {

    private SupplierDao implementation;
    private Supplier supplier;
    private Supplier supplier2;

    public SupplierDaoTest(SupplierDao implementation) {
        this.implementation = implementation;
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
    }
}