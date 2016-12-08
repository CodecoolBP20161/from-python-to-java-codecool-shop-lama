package com.codecool.shop.controller;

import com.codecool.shop.order.implementation.Order;
import org.junit.Test;
import org.mockito.Matchers;
import spark.Request;
import spark.Response;
import spark.Session;

import java.sql.SQLException;
import java.util.regex.Matcher;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

public class OrderControllerTest {
    private Request requestMock = mock(Request.class);
    private Response responseMock = mock(Response.class);
    private Session sessionMock = mock(Session.class);
    private Order userOrderMock = mock(Order.class);


    @Test
    public void addProductToCartTest_noSession() throws SQLException {
        when(requestMock.session()).thenReturn(sessionMock);
        when(requestMock.queryParams("id")).thenReturn("1");
        when(sessionMock.attribute("userOrder")).thenReturn(null, userOrderMock);
        doNothing().when(requestMock).session().attribute("userOrder",(Order) any(Order.class));
        doNothing().when(userOrderMock).addLineItem(anyInt());
        OrderController.addProductToCart(requestMock);
        verify(userOrderMock).addLineItem(anyInt());
        verify(sessionMock, times(2)).attribute("userOrder");
        verify(sessionMock, times(1)).attribute("userOrder", (Order) any(Order.class));
    }

    @Test
    public void addProductToCartTest_withSession() throws SQLException {
        when(requestMock.session()).thenReturn(sessionMock);
        when(requestMock.queryParams("id")).thenReturn("1");
        when(sessionMock.attribute("userOrder")).thenReturn(userOrderMock);
        doNothing().when(sessionMock).attribute("userOrder", any(Order.class));
        doNothing().when(userOrderMock).addLineItem(anyInt());
        OrderController.addProductToCart(requestMock);
        verify(userOrderMock).addLineItem(anyInt());
        verify(sessionMock, times(2)).attribute("userOrder");
        verify(sessionMock, never()).attribute("userOrder", anyObject());
    }
}
