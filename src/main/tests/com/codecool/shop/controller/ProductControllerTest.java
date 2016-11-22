package com.codecool.shop.controller;

import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.order.implementation.Order;
import org.eclipse.jetty.server.session.JDBCSessionManager;
import org.junit.Before;
import org.junit.Test;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by cave on 2016.11.22..
 */
public class ProductControllerTest {
    private ProductCategory productCategory1;
    private ProductCategory productCategory2;
    private Product product1;
    private Product product2;
    private Supplier supplier1;
    private Supplier supplier2;
    private Request requestMock = mock(Request.class);
    private Response responseMock = mock(Response.class);
    private Session sessionMock = mock(Session.class);
    private Order userOrderMock = mock(Order.class);

    @Before
    public void setUp() throws Exception {
        productCategory1 = new ProductCategory("Test1", "Test1", "Test1");
        productCategory1.setId(1);
        productCategory2 = new ProductCategory("Test2", "Test2", "Test2");
        productCategory2.setId(2);
        supplier1 = new Supplier("test1", "test1");
        supplier2 = new Supplier("test2", "test2");
        product1 = new Product("test", 1.0f, "HUF", "test", productCategory1, supplier1);
        product2 = new Product("test2", 2.0f, "HUF", "test2", productCategory2, supplier2);
    }

    @Test
    public void filterByProductCategoryTest() throws Exception {
        when(requestMock.queryParams("supId")).thenReturn("2");
        when(requestMock.session()).thenReturn(sessionMock);
        when(sessionMock.attribute("userOrder")).thenReturn(userOrderMock);
        when(userOrderMock.sumProductsQuantity()).thenReturn(10);
        Map params = new HashMap();
        params.put("suppliers", new ArrayList<>(Arrays.asList(supplier1, supplier2)));
        params.put("products", new ArrayList<>(Arrays.asList(product2)));
        params.put("categories", new ArrayList<>(Arrays.asList(productCategory1, productCategory2)));
        params.put("category", productCategory2);
        params.put("supplier", new ProductCategory("All Supplier", "All Supplier", "All Supplier"));
        params.put("cart", 10);
        ModelAndView modelAndViewExpected = new ModelAndView(params, "product/index");

        ModelAndView modelAndViewResult = ProductController.renderProducts(requestMock, responseMock);

        assertEquals(modelAndViewExpected.getModel(), modelAndViewResult.getModel());
    }

}