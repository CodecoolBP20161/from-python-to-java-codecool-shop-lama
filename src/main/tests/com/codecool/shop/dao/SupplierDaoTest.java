package com.codecool.shop.dao;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Supplier;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by cave on 2016.11.21..
 */
@RunWith(Parameterized.class)
public class SupplierDaoTest {

    public SupplierDao implementation;
    private Supplier supplier;

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
        supplier = spy(new Supplier("test", "test"));
    }

    @Test
    public void addNewSupplier() throws Exception {

        implementation.add(supplier);

        assertTrue("is supplier in the list", implementation.getAll().contains(supplier));
    }

    @Test
    public void removeSupplier() throws Exception {
        implementation.add(supplier);

        implementation.remove(supplier.getId());

        assertFalse("Try to find supplier", implementation.getAll().contains(supplier));
    }


//    @Test
//    public void find() throws Exception {
//        implementation.add(mock(Supplier.class));
//        Supplier firstSupplier = implementation.getAll().get(0);
//
//        Supplier foundedSupplier = implementation.find(firstSupplier.getId());
//
//        assertEquals(firstSupplier, foundedSupplier);
//    }

//    @Test
//    public void remove() throws Exception {
//
//    }
//
//    @Test
//    public void getAll() throws Exception {
//
//    }


    @After
    public void tearDown() throws Exception {
    }
}